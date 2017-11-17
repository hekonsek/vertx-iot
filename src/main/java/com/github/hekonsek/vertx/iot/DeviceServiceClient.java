package com.github.hekonsek.vertx.iot;

import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.impl.KafkaProducerRecordImpl;
import org.apache.kafka.common.utils.Bytes;

import java.util.Date;
import java.util.List;

import static com.github.hekonsek.vertx.pipes.internal.KafkaProducerBuilder.pipeProducer;
import static io.vertx.core.Vertx.vertx;
import static io.vertx.core.json.Json.encodeToBuffer;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class DeviceServiceClient {

    public static void main(String[] args) throws InterruptedException {
        List<String> devicesIds = asList(randomUUID().toString(), randomUUID().toString(), randomUUID().toString());
        System.out.println("Devices IDs: " + devicesIds);
        KafkaProducer<String, Bytes> pipeProducer = pipeProducer(vertx());
        while (true) {
            Device device = new Device();
            device.setId(devicesIds.get(nextInt(0, 3)));
            device.setType("rpi2");
            device.setCreated(new Date());
            device.setCreated(new Date());
            device.getProperties().put("cpu", nextInt(0, 101));

            pipeProducer.write(new KafkaProducerRecordImpl<>("device", device.getId(), new Bytes(encodeToBuffer(device).getBytes())));

            Thread.sleep(1000);
        }
    }

}
