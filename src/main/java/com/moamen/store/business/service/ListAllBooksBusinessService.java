package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.*;
import com.moamen.store.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListAllBooksBusinessService extends AbstractBusinessService<ListAllBooksRequest, ListAllBooksResponse> {
    private final BookService bookService;

    public ListAllBooksBusinessService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(ListAllBooksRequest request) {
        // no business validation needed
    }

    @Override
    protected ListAllBooksResponse execute(ListAllBooksRequest request) {

        final PageRequestDto pageRequest = new PageRequestDto(request.getPage(), request.getSize());
        final PageResponseDto<BookDto> bookDtoPageResponseDto = bookService.listAllBooks(pageRequest);
        return ListAllBooksResponse.builder()
                .books(bookDtoPageResponseDto.getContent())
                .page(request.getPage())
                .size(bookDtoPageResponseDto.getSize())
                .totalPages(bookDtoPageResponseDto.getTotalPages())
                .build();
    }

}
