package com.whiteKnightz.spark.springapacheSpark.services;

import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.domain.repositories.EventEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageEventServiceImpl implements MessageEventService {
    private final EventEntityRepository repository;


    @Override
    public void persist(EventEntity eventEntity) {
        repository.persist(eventEntity);
    }
}
