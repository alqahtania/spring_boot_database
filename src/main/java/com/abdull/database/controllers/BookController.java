package com.abdull.database.controllers;

import com.abdull.database.dto.BookDto;
import com.abdull.database.entity.BookEntity;
import com.abdull.database.mappers.Mapper;
import com.abdull.database.service.BookService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
public class BookController {

    private final BookService bookService;

    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> mapper) {
        this.bookService = bookService;
        this.bookMapper = mapper;
    }


    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity savedBookEntity = bookService.createBook(isbn, bookMapper.mapFrom(bookDto));
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }
}
