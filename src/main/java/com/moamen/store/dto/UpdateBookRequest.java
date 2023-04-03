package com.moamen.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UpdateBookRequest {
    @JsonIgnore
    long id;
    String name;
    String description;
    String author;
    String bookType;
    Double price;
    String isbn;

    @JsonIgnore
    BookTypeDto bookTypeDto;

}
