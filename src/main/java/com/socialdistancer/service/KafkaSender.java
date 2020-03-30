package com.socialdistancer.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaSender {
    public void send(String id) {
//        String topicName = "inbound";
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", StringSerializer.class.getName());
//        props.put("value.serializer", StringSerializer.class.getName());
//        Producer<String, String> producer = new KafkaProducer<>(props);
//        producer.send(new ProducerRecord<String, String>(topicName,
//                id, "true"));
//        producer.close();
    }
}
