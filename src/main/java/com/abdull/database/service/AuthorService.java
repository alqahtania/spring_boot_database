package com.abdull.database.service;

import com.abdull.database.entity.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAllAuthors();
}
