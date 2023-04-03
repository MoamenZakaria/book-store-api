package com.moamen.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {
    Long id;
    String name;
    String description;
    String author;
    String bookType;
    Double price;
    String isbn;

    @JsonIgnore
    BookTypeDto bookTypeDto;
}
