package com.springcloud.springcloudshardingjdbcnew.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonUtil {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper OBJECT_MAPPER;

    public ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setDateFormat(dateFormat);
        //OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToObject(String json, String key, Class<T> clazz) {
        try {
            JsonNode rootNode = OBJECT_MAPPER.readValue(json, JsonNode.class);
            JsonNode path = rootNode.path(key);
            if (!path.isMissingNode()) {
                return jsonToObject(path.toString(), clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class JsonDateSerializer extends JsonSerializer<Date> {
        private SimpleDateFormat dateFormat;

        public JsonDateSerializer(String format) {
            dateFormat = new SimpleDateFormat(format);
        }

        @Override
        public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            String value = dateFormat.format(date);
            gen.writeString(value);
        }
    }
}
