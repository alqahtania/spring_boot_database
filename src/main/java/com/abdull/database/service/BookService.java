package com.abdull.database.service;

import com.abdull.database.entity.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);
}
