package com.moamen.store.service.impl;

import com.moamen.store.dto.BookTypeDto;
import com.moamen.store.mapper.BookTypeMapper;
import com.moamen.store.repository.BookTypeRepository;
import com.moamen.store.service.BookTypeService;
import org.springframework.stereotype.Service;

@Service
public class BookTypeServiceImpl implements BookTypeService {
    private final BookTypeMapper bookTypeMapper;
    private final BookTypeRepository bookTypeRepository;

    public BookTypeServiceImpl(BookTypeMapper bookTypeMapper, BookTypeRepository bookTypeRepository) {
        this.bookTypeMapper = bookTypeMapper;
        this.bookTypeRepository = bookTypeRepository;
    }


    @Override
    public BookTypeDto findByType(String type) {
        return bookTypeMapper.toDto(bookTypeRepository.findByTypeIgnoreCase(type));
    }
}
