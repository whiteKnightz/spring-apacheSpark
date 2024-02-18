package com.whiteKnightz.spark.springapacheSpark.domain.adaptors;

import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.domain.repositories.DataLayerEventRepository;
import com.whiteKnightz.spark.springapacheSpark.domain.repositories.EventEntityRepository;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import com.whiteKnightz.spark.springapacheSpark.exception.DomainViolationException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EventEntityRepositoryAdaptor implements EventEntityRepository {
    private final DataLayerEventRepository repository;

    @Override
    public EventEntity persist(EventEntity entity) {
        return repository.save(entity);
    }

    @Override
    public EventEntity findById(String id) throws DomainViolationException {
        return repository.findEventEntityByReference(id)
                .orElseThrow(() -> new DomainViolationException("event.entity.id.not.valid"));
    }

    @Override
    public List<EventEntity> findByType(RequestType type) {
        return repository.findEventEntityByType(type);
    }
}
