package com.moamen.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moamen.store.dto.CreateBookRequest;
import com.moamen.store.dto.ErrorResponseDto;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.model.Book;
import com.moamen.store.model.BookType;
import com.moamen.store.repository.BookRepository;
import com.moamen.store.repository.BookTypeRepository;
import com.moamen.store.repository.PromotionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {
    public static final String V_1_BOOKS = "/v1/books";
    public static final String V_1_BOOKS_ID = "/v1/books/{0}";
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
    public Long BOOK_ID_1;
    public Long BOOK_ID_2;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookTypeRepository bookTypeRepository;
    @Autowired
    PromotionRepository promotionRepository;

    @BeforeAll
    public void setup() {
        prepareData();
    }


    @AfterAll
    public void tearDown() {
        cleanup();
    }

    @Test
    @DisplayName("should return a successful response when book is created successfully")
    void createBookSuccessfully() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setName("The Alchemist");
        createBookRequest.setDescription("Fiction plus");
        createBookRequest.setAuthor("Paulo");
        createBookRequest.setBookType("Fiction 2");
        createBookRequest.setPrice(100.0);
        createBookRequest.setIsbn("12354567897777");

        MockHttpServletResponse response = this.mockMvc
                .perform(post(V_1_BOOKS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))).andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    @Test
    @DisplayName("should return a error response when creating book with duplicate isbn")
    void createBookWithDuplicateIsbn() throws Exception {

        BookType bioType = bookTypeRepository.save(new BookType(null, "Bio 2", Instant.now(), Instant.now()));
        bookRepository.save(new Book(null, "Sapiens: A Brief History of Humankind", "A book by Yuval Noah Harari", "Yuval Noah Harari", bioType, 12.99, "9780092316110", Instant.now(), Instant.now()));

        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setName("The Alchemist");
        createBookRequest.setDescription("Fiction plus");
        createBookRequest.setAuthor("Paulo");
        createBookRequest.setBookType("Fiction 2");
        createBookRequest.setPrice(100.0);
        createBookRequest.setIsbn("9780092316110");

        MockHttpServletResponse response = this.mockMvc
                .perform(post(V_1_BOOKS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))).andReturn().getResponse();
        ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getContentAsString(), ErrorResponseDto.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(errorResponseDto.getErrorCode(), ResponseCodes.DUPLICATE_ISBN_ERROR.getCode());
    }
    @Test
    @DisplayName("should return a successful response when list of books is retrieved successfully")
    void getBooksSuccessfully() throws Exception {
        MockHttpServletResponse response = this.mockMvc
                .perform(get(V_1_BOOKS)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    @DisplayName("should return a error response when updating books and all params are empty")
    void updateBookWithEmptyParams() throws Exception {
        BookType fantasyType = bookTypeRepository.save(new BookType(null, "Fantasy 88", Instant.now(), Instant.now()));
        Book bookToBeUpdated = bookRepository.save(new Book(null, "Sapiens: A Brief History of Humankind", "A book by Yuval Noah Harari", "Yuval Noah Harari", fantasyType, 12.99, "97808092316110", Instant.now(), Instant.now()));

        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setDescription("");  // ---> blank params like description is not allowed
        createBookRequest.setAuthor("moamen");


        final String urlTemplate = MessageFormat.format(V_1_BOOKS_ID, bookToBeUpdated.getId());

        MockHttpServletResponse response = this.mockMvc
                .perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))).andReturn().getResponse();
        ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getContentAsString(), ErrorResponseDto.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(errorResponseDto.getErrorCode(), ResponseCodes.BOOK_UPDATE_BLANK_PARAMS_ERROR.getCode());
    }
    @Test
    @DisplayName("should return a successful response when book is deleted successfully")
    void deleteBookSuccessfully() throws Exception {
        BookType fantasyType = bookTypeRepository.save(new BookType(null, "Fantasy 2", Instant.now(), Instant.now()));
        Book bookToBeDeleted = bookRepository.save(new Book(null, "Sapiens: A Brief History of Humankind", "A book by Yuval Noah Harari", "Yuval Noah Harari", fantasyType, 12.99, "97808092316110", Instant.now(), Instant.now()));

        final String urlTemplate = MessageFormat.format(V_1_BOOKS_ID, bookToBeDeleted.getId());

        MockHttpServletResponse response = this.mockMvc
                .perform(delete(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    @DisplayName("should return an error response when the delete request is sent for a non-existing book")
    void deleteBookWhenBookIsNotFound() throws Exception {
        final String urlTemplate = MessageFormat.format(V_1_BOOKS_ID, "101019");

        MockHttpServletResponse response = this.mockMvc
                .perform(delete(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getContentAsString(), ErrorResponseDto.class);
        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertEquals(errorResponseDto.getErrorCode(), ResponseCodes.BOOK_NOT_FOUND_ERROR.getCode());
    }

    public void prepareData() {
        // Create a book types
        BookType fictionType = bookTypeRepository.save(new BookType(null, "Fiction 2", Instant.now(), Instant.now()));
        BookType nonFictionType = bookTypeRepository.save(new BookType(null, "Non-fiction 2", Instant.now(), Instant.now()));

        // Create a books
        Book book = bookRepository.save(new Book(null, "The Great Gatsby", "A novel by F. Scott Fitzgerald", "F. Scott Fitzgerald", fictionType, 10.99, "9781234567890", Instant.now(), Instant.now()));
        Book book2 = bookRepository.save(new Book(null, "Sapiens: A Brief History of Humankind", "A book by Yuval Noah ", "Yuval Noah ", nonFictionType, 12.99, "9780062316110", Instant.now(), Instant.now()));
        BOOK_ID_1 = book.getId();
        BOOK_ID_2 = book2.getId();

    }

    private void cleanup() {
        bookRepository.deleteAll();
        promotionRepository.deleteAll();
        bookTypeRepository.deleteAll();
    }
}
