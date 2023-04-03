package com.moamen.store.business.service;

import com.moamen.store.business.service.template.AbstractBusinessService;
import com.moamen.store.dto.BookTypeDto;
import com.moamen.store.dto.CheckoutRequest;
import com.moamen.store.dto.CheckoutResponse;
import com.moamen.store.dto.PromotionDto;
import com.moamen.store.enums.ResponseCodes;
import com.moamen.store.exceptions.InvalidDataException;
import com.moamen.store.exceptions.NotFoundException;
import com.moamen.store.model.Book;
import com.moamen.store.service.BookService;
import com.moamen.store.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutBusinessService extends AbstractBusinessService<CheckoutRequest, CheckoutResponse> {

    private final BookService bookService;
    private final PromotionService promotionService;


    @Override
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void validate(CheckoutRequest checkOutRequest) {
        validateBooksAvailability(checkOutRequest);
        validatePromotion(checkOutRequest);
    }


    @Override
    protected CheckoutResponse execute(CheckoutRequest request) {
        // Get a map of book IDs to their respective quantities
        final Map<Long, Integer> bookIdToQuantityMap = getBookIdToQuantityMap(request);
        final List<Book> foundBooks = request.getFoundBooks();
        List<Long> booksIDsApplicableForPromotion = new ArrayList<>();
        double totalPrice = 0;
        double totalDiscount = 0;
        for (Book book : foundBooks) {
            final Integer quantity = bookIdToQuantityMap.get(book.getId());
            final PromotionDto promotionDto = request.getPromotionDto();
            if (Objects.nonNull(promotionDto) && StringUtils.equals(book.getBookType().getType(), promotionDto.getBookTypeId().getType())) {
                // Calculate the price per quantity of books
                final double booksPricePerQty = quantity * book.getPrice();
                // Calculate the discount per book quantity
                final double discountPerBookQty = booksPricePerQty * (promotionDto.getDiscountPercent() / 100);
                // Add the price per quantity of books to the total price
                totalPrice += booksPricePerQty;
                // Add the discount per book quantity to the total discount
                totalDiscount += discountPerBookQty;
                // Add the book ID to the list of books applicable for promotion
                booksIDsApplicableForPromotion.add(book.getId());
            } else {
                // Otherwise, add the quantity multiplied by the book price to the total price
                totalPrice += quantity * book.getPrice();
            }
        }

        return prepareCheckoutResponse(request.getPromotionDto(), booksIDsApplicableForPromotion, totalPrice, totalDiscount);
    }

    private Map<Long, Integer> getBookIdToQuantityMap(CheckoutRequest checkOutRequest) {
        return checkOutRequest.getOrderItems().stream()
                .collect(Collectors.groupingBy(CheckoutRequest.OrderItemDto::getBookId,
                        Collectors.summingInt(CheckoutRequest.OrderItemDto::getQuantity)));
    }

    private void validatePromotion(CheckoutRequest checkOutRequest) {
        if (StringUtils.isNotBlank(checkOutRequest.getPromotionCode())) {
            validatePromotionAvailability(checkOutRequest);
            validatePromotionIfApplicable(checkOutRequest);
        }
    }

    private static void validatePromotionIfApplicable(CheckoutRequest checkOutRequest) {
        final BookTypeDto bookTypePromotion = checkOutRequest.getPromotionDto().getBookTypeId();
        checkOutRequest.getFoundBooks().stream()
                .filter(book -> StringUtils.equals(book.getBookType().getType(), bookTypePromotion.getType()))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ResponseCodes.INVALID_PROMOTION_NOT_APPLICABLE.getCode(), ResponseCodes.INVALID_PROMOTION_NOT_APPLICABLE.getMessage(), checkOutRequest.getPromotionCode()));
    }

    private void validatePromotionAvailability(CheckoutRequest checkOutRequest) {
        final PromotionDto promotionDto = promotionService.findByPromotionCode(checkOutRequest.getPromotionCode());
        if (Objects.isNull(promotionDto))
            throw new InvalidDataException(ResponseCodes.INVALID_PROMOTION_CODE.getCode(), ResponseCodes.INVALID_PROMOTION_CODE.getMessage(), checkOutRequest.getPromotionCode());
        checkOutRequest.setPromotionDto(promotionDto);
    }

    private void validateBooksAvailability(CheckoutRequest checkOutRequest) {
        final List<Long> orderBooksIds = checkOutRequest.getOrderItems().stream().map(CheckoutRequest.OrderItemDto::getBookId).distinct().toList();
        final List<Book> foundBooks = bookService.findAllByIds(orderBooksIds);
        if (foundBooks.size() != orderBooksIds.size()) {
            throw new NotFoundException(ResponseCodes.INVALID_ORDER_SOME_BOOK_NOT_FOUND.getCode(), ResponseCodes.INVALID_ORDER_SOME_BOOK_NOT_FOUND.getMessage());
        }
        checkOutRequest.setFoundBooks(foundBooks);
    }

    private CheckoutResponse prepareCheckoutResponse(PromotionDto promotionDto, List<Long> booksIDsApplicableForPromotion, double totalPrice, double totalDiscount) {
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setOrderId(System.currentTimeMillis());
        checkoutResponse.setTotalPrice(new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP));
        checkoutResponse.setDiscount(new BigDecimal(totalDiscount).setScale(2, RoundingMode.HALF_UP));
        checkoutResponse.setTotalPriceAfterDiscount(new BigDecimal(totalPrice - totalDiscount).setScale(2, RoundingMode.HALF_UP));
        if (Objects.nonNull(promotionDto)) {
            final CheckoutResponse.ConsumedPromotion consumedPromotion = CheckoutResponse.ConsumedPromotion.builder()
                    .promotionCode(promotionDto.getCode())
                    .discountPercentage(promotionDto.getDiscountPercent())
                    .applicableOnBookType(promotionDto.getBookTypeId().getType())
                    .applicableOnBooksIds(booksIDsApplicableForPromotion)
                    .build();
            checkoutResponse.setConsumedPromotion(consumedPromotion);
        }
        return checkoutResponse;
    }
}
