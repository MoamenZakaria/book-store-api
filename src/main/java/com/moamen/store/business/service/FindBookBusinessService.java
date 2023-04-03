package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.FindBookRequest;
import com.moamen.store.dto.FindBookResponse;
import com.moamen.store.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBookBusinessService extends AbstractBusinessService<FindBookRequest, FindBookResponse> {
    private final BookService bookService;


    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(FindBookRequest request) {
        // no business validation needed
    }

    @Override
    protected FindBookResponse execute(FindBookRequest request) {
        final BookDto bookDto = bookService.getBookById(request.getId());
        return new FindBookResponse(bookDto);
    }
}
