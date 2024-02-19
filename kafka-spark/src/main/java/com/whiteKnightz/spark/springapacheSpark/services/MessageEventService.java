package com.whiteKnightz.spark.springapacheSpark.services;

import com.whiteKnightz.spark.springapacheSpark.domain.dto.EventDto;
import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import com.whiteKnightz.spark.springapacheSpark.exception.DomainViolationException;

import java.util.List;

public interface MessageEventService {
    void persist(EventEntity eventEntity);

    EventDto findByRef(String reference) throws DomainViolationException;

    List<EventDto> findByType(RequestType type);
}
