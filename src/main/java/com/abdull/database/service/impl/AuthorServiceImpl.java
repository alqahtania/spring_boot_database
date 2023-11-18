package com.abdull.database.service.impl;

import com.abdull.database.entity.AuthorEntity;
import com.abdull.database.repositories.AuthorRepository;
import com.abdull.database.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.abdull.database.util.Utils.convertIterableToList;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAllAuthors() {
        return convertIterableToList(authorRepository.findAll());
    }
}
