package com.moamen.store.exceptions;

import lombok.Getter;


@Getter
public class InvalidDataException extends BookStoreException {

    public InvalidDataException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public InvalidDataException(String code, String message) {
        super(code, message);
    }
}


