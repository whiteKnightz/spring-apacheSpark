package com.whiteKnightz.spark.springapacheSpark.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {
    @Value("${springapacheSpark.kafka.topic}")
    String topic;

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    KafkaMessageProducer(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    public void sendMessage(MessageEvent message) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(message));
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Can't convert the Object to string!", e);
        }
    }

}
