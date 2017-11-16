package com.github.hekonsek.vertx.iot;

import com.github.hekonsek.vertx.pipes.Pipes;
import com.github.hekonsek.vertx.pipes.SimpleFunctionRegistry;
import com.github.hekonsek.vertx.pipes.marketplace.function.elasticsearch.ElasticSearchFunction;
import com.google.common.io.Files;
import io.debezium.kafka.KafkaCluster;
import io.vertx.core.Vertx;

import java.io.IOException;

import static com.github.hekonsek.vertx.pipes.Pipe.pipe;
import static com.github.hekonsek.vertx.pipes.Pipes.pipes;
import static io.vertx.core.Vertx.vertx;

public class DeviceService {

    public static void main(String[] args) throws IOException {
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        new KafkaCluster().withPorts(2181, 9092).usingDirectory(Files.createTempDir()).deleteDataPriorToStartup(true).addBrokers(1).startup();

        Vertx vertx = vertx();

        SimpleFunctionRegistry functionRegistry = new SimpleFunctionRegistry();
        Pipes pipes = pipes(vertx, functionRegistry);

        ElasticSearchFunction elasticSearchRegistry = new ElasticSearchFunction().targetType(event -> "device_registry/default");
        functionRegistry.registerFunction("elasticSearchRegistry", elasticSearchRegistry);
        pipes.startPipe(pipe("elasticSearchRegistry", "device", "elasticSearchRegistry"));

        ElasticSearchFunction elasticSearchGrafana = new ElasticSearchFunction().targetType(event -> "device_grafana/" + event.body().get("id"));
        functionRegistry.registerFunction("elasticSearchGrafana", elasticSearchGrafana);
        pipes.startPipe(pipe("elasticSearchGrafana", "device", "elasticSearchGrafana"));
    }

}
