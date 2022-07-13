package com.mqjd.datamodel.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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

    public static <T> T jsonToObject(String json, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(json, clz);
        } catch (IOException e) {
            throw new IllegalArgumentException("write obj to string error", e);
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> clz) {
        try {
            CollectionLikeType type = OBJECT_MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, clz);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("write obj to string error", e);
        }
    }

    public static <T> T jsonToObject(InputStream inputStream, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, clz);
        } catch (IOException e) {
            throw new IllegalArgumentException("read obj from string error", e);
        }
    }
}
