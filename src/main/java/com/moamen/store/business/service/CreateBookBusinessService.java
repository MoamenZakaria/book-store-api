package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.BookTypeDto;
import com.moamen.store.dto.CreateBookRequest;
import com.moamen.store.dto.CreateBookResponse;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.exceptions.InvalidDataException;
import com.moamen.store.exceptions.NotFoundException;
import com.moamen.store.mapper.BookMapper;
import com.moamen.store.model.Book;
import com.moamen.store.service.BookService;
import com.moamen.store.service.BookTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateBookBusinessService extends AbstractBusinessService<CreateBookRequest, CreateBookResponse> {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookTypeService bookTypeService;


    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(CreateBookRequest request) {
        validateDuplicateISBN(request);
        ValidateBookType(request);
    }

    @Override
    protected CreateBookResponse execute(CreateBookRequest request) {
        final Book book = bookMapper.toEntity(request);
        final BookDto bookDto = bookService.createBook(book);
        return new CreateBookResponse(bookDto.getId());
    }

    private void validateDuplicateISBN(CreateBookRequest request) {
        final boolean isDuplicate = bookService.existsByIsbn(request.getIsbn());
        if (isDuplicate) {
            throw new InvalidDataException(ResponseCodes.DUPLICATE_ISBN_ERROR.getCode(), ResponseCodes.DUPLICATE_ISBN_ERROR.getMessage(), request.getIsbn());
        }
    }

    private void ValidateBookType(CreateBookRequest request) {
        final BookTypeDto type = bookTypeService.findByType(request.getBookType());
        if (Objects.isNull(type)) {
            throw new NotFoundException(ResponseCodes.BOOK_TYPE_NOT_FOUND_ERROR.getCode(), ResponseCodes.BOOK_TYPE_NOT_FOUND_ERROR.getMessage(), request.getBookType());
        }
        request.setBookTypeDto(type);

    }

}
