package com.moamen.store.service.impl;

import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.PageRequestDto;
import com.moamen.store.dto.PageResponseDto;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.exceptions.NotFoundException;
import com.moamen.store.mapper.BookMapper;
import com.moamen.store.model.Book;
import com.moamen.store.repository.BookRepository;
import com.moamen.store.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public PageResponseDto<BookDto> listAllBooks(PageRequestDto pageRequest) {
        final int offSet = pageRequest.getPage() - 1;
        Pageable pageable = PageRequest.of(offSet, pageRequest.getSize());
        final Page<Book> booksPage = bookRepository.findAll(pageable);
        PageResponseDto.PageResponseDtoBuilder<BookDto> pageDtoBuilder = PageResponseDto.builder();

        return pageDtoBuilder.page(booksPage.getNumber())
                .totalPages(booksPage.getTotalPages())
                .size(booksPage.getSize())
                .content(bookMapper.toDtoList(booksPage.toList()))
                .build();

    }

    @Override
    public List<Book> findAllByIds(List<Long> ids) {
        return bookRepository.findAllByIdIn(ids);
    }

    @Override
    public BookDto getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return bookMapper.toDto(book.get());
        } else {
            throw new NotFoundException(ResponseCodes.BOOK_NOT_FOUND_ERROR.getCode(), ResponseCodes.BOOK_NOT_FOUND_ERROR.getMessage(), id);
        }
    }

    @Override
    public BookDto createBook(Book book) {
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId());
        if (existingBook.isPresent()) {
            return bookMapper.toDto(bookRepository.save(book));
        } else {
            throw new NotFoundException(ResponseCodes.BOOK_NOT_FOUND_ERROR.getCode(), ResponseCodes.BOOK_NOT_FOUND_ERROR.getMessage(), book.getId());
        }
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new NotFoundException(ResponseCodes.BOOK_NOT_FOUND_ERROR.getCode(), ResponseCodes.BOOK_NOT_FOUND_ERROR.getMessage(), id);
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public boolean existsByBookIdAndIsbn(Long bookId, String isbn) {
        return bookRepository.existsByIdIsNotAndIsbn(bookId, isbn);
    }
}
