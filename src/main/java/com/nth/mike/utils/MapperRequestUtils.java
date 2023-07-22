package com.nth.mike.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperRequestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T mapToModel(String json, Class<T> modelClass) {
        try {
            return objectMapper.readValue(json, modelClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
