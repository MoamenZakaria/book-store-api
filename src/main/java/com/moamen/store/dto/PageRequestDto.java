package com.moamen.store.dto;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageRequestDto {

    @Min(value = 1, message = "Page number must be greater than 0")
    int page = 1;
    @Min(value = 1, message = "Page size must be greater than 0")
    int size = 10;
}
