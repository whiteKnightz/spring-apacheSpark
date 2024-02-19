package com.whiteKnightz.spark.springapacheSpark.services;

import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;

public interface MessageEventService {
    void persist(EventEntity eventEntity);
}
