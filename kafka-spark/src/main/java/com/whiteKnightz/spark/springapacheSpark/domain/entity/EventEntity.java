package com.whiteKnightz.spark.springapacheSpark.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whiteKnightz.spark.springapacheSpark.domain.SerializerDeserializer.MapSerializerDeserializer;
import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;
import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity(name = "event_entity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Id
    @org.springframework.data.annotation.Id
    private String reference;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String modelValues;
    @Column(columnDefinition = "TEXT")
    private String value;
    @Enumerated(EnumType.STRING)
    private RequestType type;

    public static EventEntity createFromMessageEvent(MessageEvent event){
        return EventEntity.builder()
                .reference(UUID.randomUUID().toString())
                .name(event.getName())
                .value(event.getValue())
                .type(event.getRequestType())
                .build();
    }

    public Map<String, Object> getMap() {
        try {
            return MapSerializerDeserializer.deserializeMap(this.modelValues);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setMap(Map<String, Object> map) {
        try {
            this.modelValues = MapSerializerDeserializer.serializeMap(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
