package com.mqjd.datamodel.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static JsonNode read(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("value is not a valid json string", e);
        }
    }

    public static JsonNode read(InputStream inputStream) {
        try {
            return OBJECT_MAPPER.readTree(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("inputStream is not a valid json data", e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException("write obj to string error", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(json, clz);
        } catch (IOException e) {
            throw new IllegalArgumentException("write obj to string error", e);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, clz);
        } catch (IOException e) {
            throw new IllegalArgumentException("write obj to string error", e);
        }
    }
}
