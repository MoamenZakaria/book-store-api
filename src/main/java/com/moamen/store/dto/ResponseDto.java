package com.moamen.store.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto<T> {

    private String code;
    private String message;
    private T data;
}
