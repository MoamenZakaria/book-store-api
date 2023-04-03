package com.moamen.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moamen.store.dto.CheckoutRequest;
import com.moamen.store.dto.ErrorResponseDto;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.model.Book;
import com.moamen.store.model.BookType;
import com.moamen.store.model.Promotion;
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

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {
    public static final String V_1_ORDERS_CHECKOUT = "/v1/orders/checkout";

    public long BOOK_ID_1;
    public long BOOK_ID_2;
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
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
    @DisplayName("should return a successful response when the checkout request is valid and with promotion code")
    void checkoutWhenRequestIsValid() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderItems(List.of(new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1), new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1)));
        request.setPromotionCode("SUMMER_SALE_2023");

        MockHttpServletResponse response = this.mockMvc.perform(post(V_1_ORDERS_CHECKOUT)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    @DisplayName("should return a successful response when the checkout request is valid and without promotion code")
    void checkoutWhenRequestIsValidWithoutPromotionCode() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderItems(List.of(new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1), new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1)));

        MockHttpServletResponse response = this.mockMvc.perform(post(V_1_ORDERS_CHECKOUT)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    @DisplayName("should return a error response when the checkout request include invalid book id")
    void checkoutWhenRequestIncludeInvalidBookId() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderItems(List.of(new CheckoutRequest.OrderItemDto(587900L, 1), new CheckoutRequest.OrderItemDto(BOOK_ID_1, 1)));
        request.setPromotionCode("SUMMER_SALE_2023");

        MockHttpServletResponse response = this.mockMvc.perform(post(V_1_ORDERS_CHECKOUT)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        ErrorResponseDto errorResponseDto = objectMapper
                .readValue(response.getContentAsString(), ErrorResponseDto.class);

        assertEquals(ResponseCodes.INVALID_ORDER_SOME_BOOK_NOT_FOUND.getCode(), errorResponseDto.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    @DisplayName("should return an error response when the checkout request includes an invalid promotion code")
    void checkoutWhenRequestIncludesInvalidPromotionCode() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderItems(List.of(new CheckoutRequest.OrderItemDto(BOOK_ID_1, 1), new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1)));
        request.setPromotionCode("INVALID_PROMO_2023");

        MockHttpServletResponse response = this.mockMvc.perform(post(V_1_ORDERS_CHECKOUT)
                .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getContentAsString(), ErrorResponseDto.class);
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertEquals(errorResponseDto.getErrorCode(), ResponseCodes.INVALID_PROMOTION_CODE.getCode());

    }

    @Test
    @DisplayName("should return an error response when the checkout request all books are not applicable for the promotion")
    void checkoutWhenRequestAllBooksAreNotApplicableForThePromotion() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderItems(List.of(new CheckoutRequest.OrderItemDto(BOOK_ID_1, 1), new CheckoutRequest.OrderItemDto(BOOK_ID_2, 1)));
        request.setPromotionCode("BK_SALE_2023");

        MockHttpServletResponse response = this.mockMvc.perform(post(V_1_ORDERS_CHECKOUT)
                .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getContentAsString(), ErrorResponseDto.class);
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertEquals(errorResponseDto.getErrorCode(), ResponseCodes.INVALID_PROMOTION_NOT_APPLICABLE.getCode());

    }


    public void prepareData() {
        // Create a book types
        BookType fictionType = bookTypeRepository.save(new BookType(null, "Fiction 1", Instant.now(), Instant.now()));
        BookType nonFictionType = bookTypeRepository.save(new BookType(null, "Non-fiction 1", Instant.now(), Instant.now()));
        BookType comicType = bookTypeRepository.save(new BookType(null, "Comic 1", Instant.now(), Instant.now()));
        // Create a promotion
        promotionRepository.save(new Promotion(null, "SPRING_SALE_2023", fictionType, 25.0, Instant.now(), Instant.now()));
        promotionRepository.save(new Promotion(null, "SUMMER_SALE_2023", nonFictionType, 15.0, Instant.now(), Instant.now()));
        promotionRepository.save(new Promotion(null, "BK_SALE_2023", comicType, 10.0, Instant.now(), Instant.now()));
        // Create a books
        Book book = bookRepository.save(new Book(null, "The Great Gatsby", "A novel by F. Scott Fitzgerald", "F. Scott Fitzgerald", fictionType, 10.99, "9781234567890", Instant.now(), Instant.now()));
        Book book2 = bookRepository.save(new Book(null, "Sapiens: A Brief History of Humankind", "A book by Yuval Noah Harari", "Yuval Noah Harari", nonFictionType, 12.99, "9780062316110", Instant.now(), Instant.now()));
        BOOK_ID_1 = book.getId();
        BOOK_ID_2 = book2.getId();

    }

    public void cleanup() {
        bookRepository.deleteAll();
        promotionRepository.deleteAll();
        bookTypeRepository.deleteAll();
    }


}

