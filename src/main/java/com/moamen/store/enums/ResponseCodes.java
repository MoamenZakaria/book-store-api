package com.moamen.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ResponseCodes {
    SUCCESS("1", "Success"),
    INVALID_REQUEST_DATA("1000", "Invalid request data {0}"),
    SERVICE_ERROR("2001", "Service error"),
    DUPLICATE_ISBN_ERROR("2002", "Book with ISBN : {0} already exists"),
    BOOK_NOT_FOUND_ERROR("2003", "Book not found with ID: {0}"),
    BOOK_UPDATE_ALL_NULLS_ERROR("2004", "All Book fields are nulls"),
    BOOK_UPDATE_BLANK_PARAMS_ERROR("2005", "Some of Book fields are blank/whitespaces"),
    BOOK_TYPE_NOT_FOUND_ERROR("2006", "Invalid Book Type: {0}"),
    INVALID_ORDER_SOME_BOOK_NOT_FOUND("2007", "Invalid Order, Some of the books not found"),
    INVALID_PROMOTION_CODE("2008", "Invalid Promotion Code: {0}"),
    INVALID_PROMOTION_NOT_APPLICABLE("2009", "Invalid Promotion {0}, Not applicable for this order items"),
    UNEXPECTED_ERROR("9999", "Unexpected error occurred");

    private final String code;
    private final String message;

}
