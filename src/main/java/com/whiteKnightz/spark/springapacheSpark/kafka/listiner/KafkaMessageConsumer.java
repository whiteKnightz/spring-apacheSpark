package com.whiteKnightz.spark.springapacheSpark.kafka.listiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    @Value("${springapacheSpark.kafka.topic}")
    private String[] topics;


//    @KafkaListener(topics = "${springapacheSpark.kafka.topic}")
//    @KafkaListener(topics = "my-topic", groupId = "my-group")
    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void listen(String message) {
        // Process the received message
        System.out.println("Received Message: " + message);
    }


}
