package com.whiteKnightz.spark.springapacheSpark.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;
import com.whiteKnightz.spark.springapacheSpark.services.SparkService;
import com.whiteKnightz.spark.springapacheSpark.services.SparkServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMessageConsumer {
    private final ObjectMapper objectMapper;
    private final SparkService sparkService;


    @KafkaListener(topics = "${springapacheSpark.kafka.topic}", groupId = "${springapacheSpark.kafka.group-id}")
    public void listen(String message) {
        try {
            System.out.println("Received Message: " + message);
            MessageEvent messageEvent = objectMapper.readValue(message, MessageEvent.class);
            switch (messageEvent.getRequestType()){
                case CLUSTERING:
                    sparkService.performClustering(messageEvent);
                    break;
                case REGRESSION:
                    sparkService.performRegression(messageEvent);
                    break;
                case CLASSIFICATION:
                    sparkService.performClassification(messageEvent);
            }
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Can't parse the Object", e);
        }
    }


}
