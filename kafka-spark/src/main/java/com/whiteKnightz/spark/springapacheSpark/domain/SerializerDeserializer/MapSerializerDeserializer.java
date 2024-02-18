package com.whiteKnightz.spark.springapacheSpark.domain.SerializerDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapSerializerDeserializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serializeMap(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    public static Map<String, Object> deserializeMap(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    }

}
