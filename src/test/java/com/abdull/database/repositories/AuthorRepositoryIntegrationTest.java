package com.abdull.database.repositories;

import com.abdull.database.TestDataUtil;
import com.abdull.database.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.abdull.database.TestDataUtil.convertIterableToList;
import static com.abdull.database.TestDataUtil.createListOfAuthors;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorRepositoryIntegrationTest {

    private AuthorRepository authorRepository;


    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        Optional<AuthorEntity> result = authorRepository.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        authorRepository.save(authorC);
        List<AuthorEntity> result = convertIterableToList(authorRepository.findAll());
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        authorA.setName("Abdull");
        authorRepository.save(authorA);
        Optional<AuthorEntity> result = authorRepository.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Abdull");
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        Optional<AuthorEntity> saveResult = authorRepository.findById(authorA.getId());
        assertThat(saveResult).isPresent();
        assertThat(saveResult.get()).isEqualTo(authorA);
        authorRepository.delete(authorA);
        Optional<AuthorEntity> deleteResult = authorRepository.findById(authorA.getId());
        assertThat(deleteResult).isNotPresent();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        authorRepository.saveAll(createListOfAuthors(testAuthorA, testAuthorB, testAuthorC));
        List<AuthorEntity> authors = convertIterableToList(authorRepository.ageLessThan(50));
        assertThat(authors).containsExactly(testAuthorB, testAuthorC);

    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        authorRepository.saveAll(createListOfAuthors(testAuthorA, testAuthorB, testAuthorC));
        List<AuthorEntity> authors = convertIterableToList(authorRepository.findAuthorsWithAgeGreaterThan(50));
        assertThat(authors).containsExactly(testAuthorA);
    }
}