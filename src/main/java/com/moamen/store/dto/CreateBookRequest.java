package com.moamen.store.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBookRequest {
    @NotBlank(message = "name is required")
    String name;
    @NotBlank(message = "description is required")
    String description;
    @NotBlank(message = "author is required")
    String author;
    @NotBlank(message = "bookType is required")
    String bookType;
    @NotNull(message = "price is required")
    Double price;
    @NotBlank(message = "isbn is required")
    String isbn;
    @JsonIgnore
    BookTypeDto bookTypeDto;
}
