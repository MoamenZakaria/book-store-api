package com.moamen.store.exceptions.handler;

import com.moamen.store.dto.ErrorResponseDto;
import com.moamen.store.exceptions.InvalidDataException;
import com.moamen.store.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage());
        log.error("Exception occurred", ex);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        String message = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
        log.error("Exception occurred", ex);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(DuplicateKeyException.class)
//    public ResponseEntity<ErrorResponseDto> handleDuplicateKeyException(DuplicateKeyException ex) {
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
//    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex) {
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto(, ex.getMessage());
//        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidDataException(InvalidDataException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        String message = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}

