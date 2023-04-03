package com.moamen.store.exceptions;


import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class BookStoreException extends RuntimeException {

    final String code;
    final transient Object[] args;

    public BookStoreException(String code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public BookStoreException(String code, String message, boolean isBackend) {
        super(message);
        this.code = code;
        this.args = null;
    }

    public BookStoreException(String code, String message, Throwable e, Object... args) {
        super(message, e);
        this.code = code;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(super.getMessage(), args);
    }
}
