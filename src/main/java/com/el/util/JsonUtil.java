package com.el.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.*;

@Slf4j
public abstract class JsonUtil {

    public static ObjectMapper JSON_MAPPER;

    static {
        val javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ISO_DATE));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(ISO_TIME));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(ISO_TIME));
        //javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_DATE_TIME));
        //eg-app DateTimeFormatter "yyyy-MM-dd HH:mm"
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_DATE_TIME));
        val objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置 SerializationFeature.FAIL_ON_EMPTY_BEANS 为 false
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JSON_MAPPER = objectMapper;
    }

    public static Optional<String> toJson(Object obj) {
        try {
            return Optional.of(JSON_MAPPER.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.debug("[CORE-JSON] obj -> json failed!", e);
            return Optional.empty();
        }
    }

    public static String toJsonOrEmpty(Object obj) {
        return toJson(obj).orElse("");
    }

    public static <T> Optional<T> fromJson(String json, Class<T> objClass) {
        try {
            return Optional.of(JSON_MAPPER.readValue(json, objClass));
        } catch (IOException e) {
            log.debug("[CORE-JSON] json -> obj failed!", e);
            return Optional.empty();
        }
    }

}
