package com.moamen.store.mapper;

import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.CreateBookRequest;
import com.moamen.store.model.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {
    @Mapping(source = "bookType.type", target = "bookType")
    @Mapping(source = "bookType", target = "bookTypeDto")
    BookDto toDto(Book book);
    @Mapping(source = "bookTypeId.type", target = "bookType")
    List<BookDto> toDtoList(List<Book> books);
    @Mapping(source = "bookTypeDto", target = "bookType")
    Book toEntity(BookDto bookDto);
    @Mapping(source = "bookTypeDto", target = "bookType")
    Book toEntity(CreateBookRequest createBookRequest);


}
