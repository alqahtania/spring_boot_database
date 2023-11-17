package com.abdull.database;

import com.abdull.database.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {


    @Test
    public void testThatJacksonCanCreateJsonFromJavaObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = Book.builder()
                .isbn("isb-nnn")
                .title("Spring Boot")
                .author("Abdullah")
                .yearPublished("2023")
                .build();

        String json = objectMapper.writeValueAsString(book);

        assertThat(json).isEqualTo("{\"isbn\":\"isb-nnn\",\"title\":\"Spring Boot\",\"author\":\"Abdullah\",\"year\":\"2023\"}");
    }

    @Test
    public void testThatJacksonCanCreateJavaObjectFromJson() throws JsonProcessingException {
        Book book = Book.builder()
                .isbn("isb-nnn")
                .title("Spring Boot")
                .author("Abdullah")
                .yearPublished("2023")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"isbn\":\"isb-nnn\",\"title\":\"Spring Boot\",\"author\":\"Abdullah\",\"year\":\"2023\"}";
        Book result = objectMapper.readValue(json, Book.class);
        assertThat(result).isEqualTo(book);
    }
}
