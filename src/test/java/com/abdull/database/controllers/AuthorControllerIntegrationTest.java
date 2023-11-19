package com.abdull.database.controllers;

import com.abdull.database.TestDataUtil;
import com.abdull.database.entity.AuthorEntity;
import com.abdull.database.service.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class AuthorControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private AuthorService authorService;


    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }


    @Test
    public void testThatCreateAuthorSuccessfullyReturnHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String json = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatedAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String json = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorsReturnStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


    @Test
    public void testThatGetAuthorsReturnListType() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        );
    }

    @Test
    public void testThatGetAuthorsReturnListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        authorService.save(testAuthorA);
        authorService.save(testAuthorB);
        authorService.save(testAuthorC);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].age").value(testAuthorC.getAge())
        );
    }

    @Test
    public void testThatCanRetrieveSingleAuthorAndReturnStatus200() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        authorService.save(testAuthorA);
        authorService.save(testAuthorB);
        authorService.save(testAuthorC);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        );
    }

    @Test
    public void testThatCanRetrieveSingleAuthorAndReturnStatus404NotFound() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        authorService.save(testAuthorA);
        authorService.save(testAuthorB);
        authorService.save(testAuthorC);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatAuthorGetFullyUpdatedAndIdIsNotChangedReturned200() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);
        testAuthorA.setId(234L);
        testAuthorA.setName("Abdull Bosk");
        testAuthorA.setAge(33);
        String requestBody = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abdull Bosk")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(33)
        );
    }

     @Test
    public void testThatAuthorPutUpdateReturn404NotFound() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);
        testAuthorA.setId(234L);
        testAuthorA.setName("Abdull Bosk");
        testAuthorA.setAge(33);
        String requestBody = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/14563")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }



}