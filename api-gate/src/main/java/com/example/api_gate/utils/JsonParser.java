package com.example.api_gate.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String fileName, Class<T> clazz) throws IOException {
        InputStream inputStream = new ClassPathResource(fileName).getInputStream();
        return objectMapper.readValue(inputStream, clazz);
    }

}
