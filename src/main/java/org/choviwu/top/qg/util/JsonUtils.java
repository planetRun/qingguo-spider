package org.choviwu.top.qg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

public class JsonUtils {


    public static String toJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String ret = objectMapper.writeValueAsString(object);
            return ret;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T toObj(String jsonStr,Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonStr,tClass);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

    }
}
