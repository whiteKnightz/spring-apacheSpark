package com.whiteKnightz.spark.springapacheSpark.domain.repositories;

import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import com.whiteKnightz.spark.springapacheSpark.exception.DomainViolationException;

import java.util.List;

public interface EventEntityRepository {
    EventEntity persist(EventEntity entity);

    EventEntity findByReference(String id) throws DomainViolationException;

    List<EventEntity> findByType(RequestType type);
}
