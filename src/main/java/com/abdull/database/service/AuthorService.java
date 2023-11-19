package com.abdull.database.service;

import com.abdull.database.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAllAuthors();

    Optional<AuthorEntity> findAuthor(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void deleteById(Long id);
}
