package com.whiteKnightz.spark.springapacheSpark.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEvent implements Serializable {
    private RequestType requestType;
    private Boolean hasHeaders;
    private List<String> features;
    private String labelCol;
    private String name;
    private String value;
}
