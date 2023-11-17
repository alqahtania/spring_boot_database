package com.abdull.database.controllers;

import com.abdull.database.domain.Book;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class BookController {

    @GetMapping(path = "/books")
    public Book retrieveBook() {
        return Book.builder()
                .isbn("isb-n")
                .title("Spring Boot")
                .author("Abdullah")
                .yearPublished("2023")
                .build();
    }

    @PostMapping(path = "/books")
    public Book postBook(@RequestBody Book book) {
        log.info("Post Book ---> " + book.toString());
        return book;
    }
}
