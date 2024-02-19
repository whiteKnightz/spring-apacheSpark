package com.whiteKnightz.spark.springapacheSpark.services;

import com.whiteKnightz.spark.springapacheSpark.domain.dto.EventDto;
import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.domain.repositories.EventEntityRepository;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import com.whiteKnightz.spark.springapacheSpark.exception.DomainViolationException;
import com.whiteKnightz.spark.springapacheSpark.mapper.EventEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageEventServiceImpl implements MessageEventService {
    private final EventEntityRepository repository;
    private final EventEntityMapper eventEntityMapper;


    @Override
    public void persist(EventEntity eventEntity) {
        repository.persist(eventEntity);
    }

    @Override
    public EventDto findByRef(String reference) throws DomainViolationException {
        return eventEntityMapper.eventEntityToDto(repository.findByReference(reference));
    }

    @Override
    public List<EventDto> findByType(RequestType type) {
        return repository.findByType(type).stream().map(eventEntityMapper::eventEntityToDto)
                .collect(Collectors.toList());
    }
}
