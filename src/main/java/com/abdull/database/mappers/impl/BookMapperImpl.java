package com.abdull.database.mappers.impl;

import com.abdull.database.dto.BookDto;
import com.abdull.database.entity.BookEntity;
import com.abdull.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

    @Override
    public List<BookDto> mapTo(List<BookEntity> bookEntities) {
        return bookEntities.stream().map(this::mapTo).collect(Collectors.toList());
    }

    @Override
    public List<BookEntity> mapFrom(List<BookDto> bookDtos) {
        return bookDtos.stream().map(this::mapFrom).collect(Collectors.toList());
    }
}
