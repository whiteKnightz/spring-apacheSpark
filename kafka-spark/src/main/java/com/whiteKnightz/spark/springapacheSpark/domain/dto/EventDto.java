package com.whiteKnightz.spark.springapacheSpark.domain.dto;

import com.whiteKnightz.spark.springapacheSpark.dto.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventDto implements Serializable {
    private String reference;
    private String name;
//    private Map<String, Object> modelValues;
    private String modelValues;
    private String value;
    private RequestType type;
}
