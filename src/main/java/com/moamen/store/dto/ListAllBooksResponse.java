package com.moamen.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ListAllBooksResponse {

    int page;
    int size;
    int totalPages;
    List<BookDto> books;
}
