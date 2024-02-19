package com.whiteKnightz.spark.springapacheSpark.mapper;

import com.whiteKnightz.spark.springapacheSpark.domain.dto.EventDto;
import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EventEntityMapper {
//    @Mappings({
//            @Mapping(target = "modelValues", expression = "java(entity.getMap())")
//    })
    EventDto eventEntityToDto(EventEntity entity);

}
