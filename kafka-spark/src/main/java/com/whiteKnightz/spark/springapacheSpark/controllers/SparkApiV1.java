package com.whiteKnightz.spark.springapacheSpark.controllers;

import com.whiteKnightz.spark.springapacheSpark.domain.dto.EventDto;
import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import com.whiteKnightz.spark.springapacheSpark.exception.DomainViolationException;
import com.whiteKnightz.spark.springapacheSpark.services.MessageEventService;
import com.whiteKnightz.spark.springapacheSpark.services.SparkService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/spark")
@AllArgsConstructor
public class SparkApiV1 {
    private final SparkService sparkService;
    private final MessageEventService eventService;

    @RequestMapping("read-csv")
    public ResponseEntity<String> getRowCount() {
        return ResponseEntity.ok(sparkService.readStaticFile());
    }

    @GetMapping("/{reference}")
    public ResponseEntity<Object> findByRef(@PathVariable String reference) {
        try {
            return ResponseEntity.ok(eventService.findByRef(reference));
        } catch (DomainViolationException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/all/{type}")
    public ResponseEntity<List<EventDto>> findByType(@PathVariable RequestType type) {
        return ResponseEntity.ok(eventService.findByType(type));
    }

}
