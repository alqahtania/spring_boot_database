package com.abdull.database.service.impl;

import com.abdull.database.entity.AuthorEntity;
import com.abdull.database.repositories.AuthorRepository;
import com.abdull.database.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.abdull.database.util.Utils.convertIterableToList;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAllAuthors() {
        return convertIterableToList(authorRepository.findAll());
    }

    @Override
    public Optional<AuthorEntity> findAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author doesn't exist"));
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
