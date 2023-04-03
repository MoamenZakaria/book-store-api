package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.BookDto;
import com.moamen.store.dto.BookTypeDto;
import com.moamen.store.dto.UpdateBookRequest;
import com.moamen.store.dto.UpdateBookResponse;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.exceptions.InvalidDataException;
import com.moamen.store.exceptions.NotFoundException;
import com.moamen.store.mapper.BookMapper;
import com.moamen.store.service.BookService;
import com.moamen.store.service.BookTypeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateBookBusinessService extends AbstractBusinessService<UpdateBookRequest, UpdateBookResponse> {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookTypeService bookTypeService;

    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(UpdateBookRequest request) {
        Supplier<Stream<String>> updateableParamsSupplier = getUpdateableParamsSupplier(request);
        validateIfAllFieldsAreNull(updateableParamsSupplier.get());
        validateIfAnyIfAnyFieldBlank(updateableParamsSupplier.get());
        validateIfIsbnAssociatedWithOtherBook(request);
        ValidateBookType(request);
    }

    @Override
    protected UpdateBookResponse execute(UpdateBookRequest request) {
        final BookDto oldBookDto = bookService.getBookById(request.getId());
        final BookDto mergeBookDto = mergeBookDtos(request, oldBookDto);
        final BookDto bookDto = bookService.updateBook(bookMapper.toEntity(mergeBookDto));
        return new UpdateBookResponse(bookDto);
    }

    private void validateIfIsbnAssociatedWithOtherBook(UpdateBookRequest request) {
        if (StringUtils.isNotBlank(request.getIsbn())) {
            final boolean isIsbnAssociatedWithOtherBook = bookService.existsByBookIdAndIsbn(request.getId(), request.getIsbn());
            if (isIsbnAssociatedWithOtherBook) {
                throw new InvalidDataException(ResponseCodes.DUPLICATE_ISBN_ERROR.getCode(), ResponseCodes.DUPLICATE_ISBN_ERROR.getMessage(), request.getIsbn());
            }
        }
    }

    public void validateIfAllFieldsAreNull(Stream<String> params) {
        boolean areAllNull = validateParams(params, Objects::isNull, Stream::allMatch);

        if (areAllNull) {
            throw new InvalidDataException(ResponseCodes.BOOK_UPDATE_ALL_NULLS_ERROR.getCode(), ResponseCodes.BOOK_UPDATE_ALL_NULLS_ERROR.getMessage());
        }
    }

    public void validateIfAnyIfAnyFieldBlank(Stream<String> params) {
        boolean isAnyParamBlank = validateParams(params, StringUtils::isWhitespace, Stream::anyMatch);

        if (isAnyParamBlank) {
            throw new InvalidDataException(ResponseCodes.BOOK_UPDATE_BLANK_PARAMS_ERROR.getCode(), ResponseCodes.BOOK_UPDATE_BLANK_PARAMS_ERROR.getMessage());
        }
    }

    private BookDto mergeBookDtos(UpdateBookRequest request, BookDto oldBookDto) {
        if (StringUtils.isNotBlank(request.getName())) {
            oldBookDto.setName(request.getName());
        }
        if (StringUtils.isNotBlank(request.getDescription())) {
            oldBookDto.setDescription(request.getDescription());
        }
        if (StringUtils.isNotBlank(request.getAuthor())) {
            oldBookDto.setAuthor(request.getAuthor());
        }
        if (StringUtils.isNotBlank(request.getIsbn())) {
            oldBookDto.setIsbn(request.getIsbn());
        }
        if (request.getPrice() != null) {
            oldBookDto.setPrice(request.getPrice());
        }
        if (StringUtils.isNotBlank(request.getBookType()) && !StringUtils.equalsIgnoreCase(request.getBookType(), oldBookDto.getBookType())) {
            oldBookDto.setBookType(request.getBookType());
            oldBookDto.setBookTypeDto(request.getBookTypeDto());
        }

        return oldBookDto;
    }

    private void ValidateBookType(UpdateBookRequest request) {
        if (StringUtils.isNotBlank(request.getBookType())) {
            final BookTypeDto type = bookTypeService.findByType(request.getBookType());
            if (Objects.isNull(type)) {
                throw new NotFoundException(ResponseCodes.BOOK_TYPE_NOT_FOUND_ERROR.getCode(), ResponseCodes.BOOK_TYPE_NOT_FOUND_ERROR.getMessage(), request.getBookType());
            }
            request.setBookTypeDto(type);
        }
    }

    public static <T> boolean validateParams(@NonNull Stream<T> stream, @NonNull Predicate<T> predicate, @NonNull BiFunction<Stream<T>, Predicate<T>, Boolean> allMatchFunction) {
        return allMatchFunction.apply(stream, predicate);
    }

    private static Supplier<Stream<String>> getUpdateableParamsSupplier(UpdateBookRequest request) {
        return () -> Stream.of(
                request.getName(),
                request.getDescription(),
                request.getAuthor(),
                request.getBookType(),
                request.getPrice()==null?null:request.getPrice().toString(),
                request.getIsbn()
        );
    }
}
