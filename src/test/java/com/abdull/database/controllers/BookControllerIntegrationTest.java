package com.abdull.database.controllers;

import com.abdull.database.TestDataUtil;
import com.abdull.database.entity.AuthorEntity;
import com.abdull.database.entity.BookEntity;
import com.abdull.database.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Log
public class BookControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }



    @Test
    public void testThatBookGetsCreatedAndReturn201CreatedStatus() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatBookGetsCreatedAndReturn201CreatedStatusWithNullAuthorAndSavedBookRetrievedAndReturned() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/1234-bn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1234-bn")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }

    @Test
    public void testThatBookGetsCreatedAndReturn201CreatedStatusWithSavedBookAndAuthorRetrievedAndReturned() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        BookEntity testBookA = TestDataUtil.createTestBookA(testAuthorA);
        String bookJson = objectMapper.writeValueAsString(testBookA);
        log.info("json created -----> " + bookJson);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/1234-bn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1234-bn")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.age").value(80)
        );
    }

    @Test
    public void testThatGetBooksReturnStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnListOfAllBooks() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        BookEntity testBookB = TestDataUtil.createTestBookB(null);
        bookService.createBook("123456", testBookA);
        bookService.createBook("23453", testBookB);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("123456")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].title").value(testBookB.getTitle())
        );

    }
}
