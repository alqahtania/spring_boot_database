package com.abdull.database.controllers;

import com.abdull.database.dto.BookDto;
import com.abdull.database.dto.CustomPageDto;
import com.abdull.database.entity.BookEntity;
import com.abdull.database.mappers.Mapper;
import com.abdull.database.service.BookService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/books")
    public ResponseEntity<CustomPageDto<BookDto>> listAllBooks(Pageable pageable) {
        Page<BookEntity> booksPage = bookService.findAll(pageable);
        List<BookDto> books = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        CustomPageDto<BookDto> customPageDto = CustomPageDto.<BookDto>builder()
                .content(books)
                .totalPages(booksPage.getTotalPages())
                .totalElements(booksPage.getTotalElements())
                .first(booksPage.isFirst())
                .last(booksPage.isLast())
                .size(booksPage.getSize())
                .page(booksPage.getNumber())
                .build();
        return new ResponseEntity<>(customPageDto, HttpStatus.OK);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> readOneBook(@PathVariable String isbn) {
        Optional<BookEntity> optionalBookEntity = bookService.findBook(isbn);
        return optionalBookEntity.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
