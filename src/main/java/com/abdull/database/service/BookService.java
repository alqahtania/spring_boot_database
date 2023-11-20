package com.abdull.database.service;

import com.abdull.database.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);
    List<BookEntity> findAllBooks();
    Optional<BookEntity> findBook(String isbn);
    Page<BookEntity> findAll(Pageable pageable);
}
