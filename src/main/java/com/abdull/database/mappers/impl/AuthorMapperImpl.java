package com.abdull.database.mappers.impl;

import com.abdull.database.dto.AuthorDto;
import com.abdull.database.entity.AuthorEntity;
import com.abdull.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

    @Override
    public List<AuthorDto> mapTo(List<AuthorEntity> authorEntities) {
        return authorEntities.stream().map(this::mapTo).collect(Collectors.toList());
    }

    @Override
    public List<AuthorEntity> mapFrom(List<AuthorDto> authorDtos) {
        return authorDtos.stream().map(this::mapFrom).collect(Collectors.toList());
    }
}
