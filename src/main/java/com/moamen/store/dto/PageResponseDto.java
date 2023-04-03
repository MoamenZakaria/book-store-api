package com.moamen.store.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponseDto<T> {

    List<T> content;
    int page;
    int size;
    int totalPages;
}
