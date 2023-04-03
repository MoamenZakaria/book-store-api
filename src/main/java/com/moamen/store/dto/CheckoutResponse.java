package com.moamen.store.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutResponse {
    Long orderId;
    BigDecimal totalPrice;
    BigDecimal totalPriceAfterDiscount;
    BigDecimal discount;
    ConsumedPromotion consumedPromotion;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ConsumedPromotion {

        String promotionCode;
        Double discountPercentage;
        String applicableOnBookType;
        List<Long> applicableOnBooksIds;


    }
}
