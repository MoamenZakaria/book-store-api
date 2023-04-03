package com.moamen.store.controller.v1;


import com.moamen.store.business.service.*;
import com.moamen.store.dto.*;
import com.moamen.store.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final ResponseUtil responseUtil;
    private final CreateBookBusinessService createBookBusinessService;
    private final ListAllBooksBusinessService listAllBooksBusinessService;
    private final UpdateBookBusinessService updateBookBusinessService;
    private final DeleteBookBusinessService deleteBookBusinessService;
    private final FindBookBusinessService findBookBusinessService;

    @Operation(summary = "create book and return book id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Book updated successfully"),
            @ApiResponse(responseCode = "2002", description = "Book with ISBN : {xyz} already exists"),
            @ApiResponse(responseCode = "2003", description = "Book not found with ID: {xyz}"),
            @ApiResponse(responseCode = "2006", description = "Invalid Book Type: {xyz}"),
    })
    @PostMapping
    public ResponseEntity<ResponseDto<CreateBookResponse>> createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        return responseUtil.buildSuccessResponse(createBookBusinessService.process(createBookRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "List all books with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Success"),
            @ApiResponse(responseCode = "2003", description = "Book not found with ID: {xyz}"),
    })
    @GetMapping
    public ResponseEntity<ResponseDto<ListAllBooksResponse>> listAllBooks(@Valid ListAllBooksRequest listAllBooksRequest) {
        return responseUtil.buildSuccessResponse(listAllBooksBusinessService.process(listAllBooksRequest));
    }

    @Operation(summary = "Find book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Success"),
            @ApiResponse(responseCode = "2003", description = "Book not found with ID: {xyz}"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<FindBookResponse>> getBookById(@PathVariable Long id) {
        return responseUtil.buildSuccessResponse(findBookBusinessService.process(new FindBookRequest(id)));
    }

    @Operation(summary = "Update book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Success"),
            @ApiResponse(responseCode = "2002", description = "Book with ISBN : {xyz} already exists"),
            @ApiResponse(responseCode = "2003", description = "Book not found with ID: {xyz}"),
            @ApiResponse(responseCode = "2004", description = "All Book fields are nulls"),
            @ApiResponse(responseCode = "2005", description = "Some of Book fields are blank/whitespaces"),
            @ApiResponse(responseCode = "2006", description = "Invalid Book Type: {xyz}"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UpdateBookResponse>> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest updateBookRequest) {
        updateBookRequest.setId(id);
        return responseUtil.buildSuccessResponse(updateBookBusinessService.process(updateBookRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Success"),
            @ApiResponse(responseCode = "2002", description = "Book with ISBN : {xyz} already exists"),
            @ApiResponse(responseCode = "2003", description = "Book not found with ID: {xyz}"),
            @ApiResponse(responseCode = "2006", description = "Invalid Book Type: {xyz}"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<DeleteBookResponse>> deleteBook(@PathVariable Long id) {
        return responseUtil.buildSuccessResponse(deleteBookBusinessService.process(new DeleteBookRequest(id)));

    }
}
