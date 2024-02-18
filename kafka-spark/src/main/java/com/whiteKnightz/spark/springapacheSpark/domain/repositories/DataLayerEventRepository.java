package com.whiteKnightz.spark.springapacheSpark.domain.repositories;

import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataLayerEventRepository extends CrudRepository<EventEntity, String> {

    List<EventEntity> findEventEntityByType(RequestType type);

    Optional<EventEntity> findEventEntityByReference(String reference);
}
