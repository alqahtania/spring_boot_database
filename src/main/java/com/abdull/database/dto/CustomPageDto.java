package com.abdull.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomPageDto<T> {
    private List<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Boolean last;
    private Integer size;
    private Integer page;
}
