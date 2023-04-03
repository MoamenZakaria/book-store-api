package com.moamen.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moamen.store.model.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutRequest {
    @NotNull(message = "Order items cannot be null")
    @NotEmpty(message = "Order items cannot be empty")
    private List<@Valid OrderItemDto> orderItems;
    private String promotionCode;
    @JsonIgnore
    List<Book> foundBooks;
    @JsonIgnore
    PromotionDto promotionDto;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderItemDto {
        @NotNull(message = "book id can't be null")
        @Positive(message = "quantity must be positive")
        Long bookId;
        @NotNull(message = "quantity can't be null")
        @Positive(message = "quantity must be positive")
        Integer quantity;
    }
}
