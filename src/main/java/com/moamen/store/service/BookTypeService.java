package com.moamen.store.service;

import com.moamen.store.dto.BookTypeDto;

public interface BookTypeService {

    BookTypeDto findByType(String type);
}

