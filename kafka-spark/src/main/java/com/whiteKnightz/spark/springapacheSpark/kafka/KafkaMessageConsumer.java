package com.whiteKnightz.spark.springapacheSpark.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    private final ObjectMapper objectMapper;

    KafkaMessageConsumer(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }


    @KafkaListener(topics = "${springapacheSpark.kafka.topic}", groupId = "${springapacheSpark.kafka.group-id}")
    public void listen(String message) {
        try {
            MessageEvent messageEvent = objectMapper.readValue(message, MessageEvent.class);
            System.out.println("Received Message: " + message);
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Can't parse the Object", e);
        }
    }


}
