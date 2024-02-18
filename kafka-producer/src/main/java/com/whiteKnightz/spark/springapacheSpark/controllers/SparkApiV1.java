package com.whiteKnightz.spark.springapacheSpark.controllers;

import com.whiteKnightz.spark.springapacheSpark.kafka.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/kafka")
public class SparkApiV1 {
    @Autowired
    private KafkaMessageProducer messageProducer;

    @RequestMapping("publishMessage")
    public ResponseEntity<Boolean> publishMessage(@RequestBody(required = true) String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
