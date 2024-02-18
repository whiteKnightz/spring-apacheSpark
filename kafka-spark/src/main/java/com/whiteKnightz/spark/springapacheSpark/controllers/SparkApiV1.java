package com.whiteKnightz.spark.springapacheSpark.controllers;

import com.whiteKnightz.spark.springapacheSpark.services.SparkService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/spark")
@AllArgsConstructor
public class SparkApiV1 {
    private final SparkService sparkService;

    @RequestMapping("read-csv")
    public ResponseEntity<String> getRowCount() {
        return ResponseEntity.ok(sparkService.readStaticFile());
    }

}
