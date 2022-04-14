package com.mqjd.datamodel.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class DataType {
    @JsonProperty("$ref")
    public String ref;

    @JsonProperty("$testLong")
    private Long testLong;

    @JsonProperty("$testDouble")
    private Double testDouble;

    @JsonProperty("$testMap")
    private Map<String, Object> testMap;

    @JsonProperty("$testList")
    private List<String> testList;

    public int plus(int a, int b) {
        return a + b;
    }
}
