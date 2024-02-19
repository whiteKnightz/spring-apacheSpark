package com.whiteKnightz.spark.springapacheSpark.services;

import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;

public interface SparkService {
    String readStaticFile();

    void performClustering(MessageEvent messageEvent);

    void performRegression(MessageEvent messageEvent);

    void performClassification(MessageEvent messageEvent);
}
