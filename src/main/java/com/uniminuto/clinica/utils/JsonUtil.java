package com.uniminuto.clinica.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            if (obj == null) return null;
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "Error convirtiendo JSON: " + e.getMessage();
        }
    }
}

