package com.github.hekonsek.vertx.iot;

import io.vertx.kafka.client.producer.impl.KafkaProducerRecordImpl;
import org.apache.kafka.common.utils.Bytes;

import java.util.Date;

import static com.github.hekonsek.vertx.pipes.internal.KafkaProducerBuilder.pipeProducer;
import static io.vertx.core.Vertx.vertx;
import static io.vertx.core.json.Json.encodeToBuffer;

public class DeviceServiceClient {

    public static void main(String[] args) {
        Device device = new Device();
        device.setId("deviceId");
        device.setType("rpi2");
        device.setCreated(new Date());

        pipeProducer(vertx()).write(new KafkaProducerRecordImpl<>("device", device.getId(), new Bytes(encodeToBuffer(device).getBytes())));
    }

}
