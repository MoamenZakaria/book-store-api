package com.moamen.store.util;

import com.moamen.store.dto.ResponseDto;
import com.moamen.store.enums.ResponseCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public <T> ResponseEntity<ResponseDto<T>> buildSuccessResponse(T data, HttpStatus httpStatus) {
        ResponseDto<T> responseDto = new ResponseDto<>(ResponseCodes.SUCCESS.getCode(), ResponseCodes.SUCCESS.getMessage(), data);
        return ResponseEntity.status(httpStatus).body(responseDto);
    }

    public <T> ResponseEntity<ResponseDto<T>> buildSuccessResponse(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>(ResponseCodes.SUCCESS.getCode(), ResponseCodes.SUCCESS.getMessage(), data);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
