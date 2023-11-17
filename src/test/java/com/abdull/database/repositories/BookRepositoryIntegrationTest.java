package com.abdull.database.repositories;

import com.abdull.database.TestDataUtil;
import com.abdull.database.domain.Author;
import com.abdull.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryIntegrationTest {

    private BookRepository bookRepository;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookB(author);
        bookRepository.save(book);
        book.setTitle("UPDATED");
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
    }
}