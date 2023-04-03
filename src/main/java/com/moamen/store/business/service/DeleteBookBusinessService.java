package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.DeleteBookRequest;
import com.moamen.store.dto.DeleteBookResponse;
import com.moamen.store.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class DeleteBookBusinessService extends AbstractBusinessService<DeleteBookRequest, DeleteBookResponse> {

    private final BookService bookService;


    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(DeleteBookRequest request) {
        // no business validation needed
    }

    @Override
    protected DeleteBookResponse execute(DeleteBookRequest request) {
        final Long bookId = request.getId();
        bookService.deleteBook(bookId);

        return new DeleteBookResponse(bookId);
    }


}
