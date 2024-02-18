package com.whiteKnightz.spark.springapacheSpark.controllers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/spark")
public class SparkApiV1 {
    @Autowired
    private SparkSession sparkSession;

    @RequestMapping("read-csv")
    public ResponseEntity<String> getRowCount() {
        Dataset<Row> dataset = sparkSession.read().option("header", "true").csv("../apacheSpark/src/main/resources/raw_data.csv");
        String html = String.format("<h1>%s</h1>", "Running Apache Spark on/with support of Spring boot") +
                String.format("<h2>%s</h2>", "Spark version = "+sparkSession.sparkContext().version()) +
                String.format("<h3>%s</h3>", "Read csv..") +
                String.format("<h4>Total records %d</h4>", dataset.count()) +
                String.format("<h5>Schema <br/> %s</h5> <br/> Sample data - <br/>", dataset.schema().treeString()) +
                dataset.showString(20, 20, true);
        return ResponseEntity.ok(html);
    }

}
