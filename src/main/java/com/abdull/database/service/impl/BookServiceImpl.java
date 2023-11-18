package com.abdull.database.service.impl;

import com.abdull.database.entity.BookEntity;
import com.abdull.database.repositories.BookRepository;
import com.abdull.database.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.abdull.database.util.Utils.convertIterableToList;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAllBooks() {
        return convertIterableToList(bookRepository.findAll());
    }
}
