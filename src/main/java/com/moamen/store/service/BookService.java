package com.moamen.store.service;

import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.PageRequestDto;
import com.moamen.store.dto.PageResponseDto;
import com.moamen.store.model.Book;

import java.util.List;

public interface BookService {
    PageResponseDto<BookDto> listAllBooks(PageRequestDto pageRequest);

    List<Book> findAllByIds(List<Long> ids);


    BookDto getBookById(Long id);

    BookDto createBook(Book book);

    BookDto updateBook(Book book);

    void deleteBook(Long id);

    boolean existsByIsbn(String isbn);

    boolean existsByBookIdAndIsbn(Long bookId, String isbn);
}

