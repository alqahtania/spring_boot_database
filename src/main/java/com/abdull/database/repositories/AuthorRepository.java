package com.abdull.database.repositories;

import com.abdull.database.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Iterable<Author> ageLessThan(int age); // Spring JPA is smart enough to create the implementation from the method name age less than

    @Query("SELECT a FROM Author a where a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}
