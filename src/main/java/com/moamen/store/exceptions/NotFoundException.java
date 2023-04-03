package com.moamen.store.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends BookStoreException {


    public NotFoundException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
