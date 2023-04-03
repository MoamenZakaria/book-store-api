package com.moamen.store.controller.v1;


import com.moamen.store.business.service.CheckoutBusinessService;
import com.moamen.store.dto.CheckoutRequest;
import com.moamen.store.dto.CheckoutResponse;
import com.moamen.store.dto.ResponseDto;
import com.moamen.store.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final ResponseUtil responseUtil;
    private final CheckoutBusinessService checkoutBusinessService;

    public OrderController(ResponseUtil responseUtil, CheckoutBusinessService checkoutBusinessService) {
        this.responseUtil = responseUtil;
        this.checkoutBusinessService = checkoutBusinessService;
    }

    @Operation(summary = "Create order and apply promotions if any")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1", description = "Order created successfully"),
            @ApiResponse(responseCode = "2007", description = "Invalid Order, Some of the books not found"),
            @ApiResponse(responseCode = "2008", description = "Invalid Promotion Code: PROMO_xyz"),
            @ApiResponse(responseCode = "2009", description = "Invalid Promotion PROMO_xyz, Not applicable for this order items")})
    @PostMapping("/checkout")
    public ResponseEntity<ResponseDto<CheckoutResponse>> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return responseUtil.buildSuccessResponse(checkoutBusinessService.process(checkoutRequest), HttpStatus.CREATED);
    }
}
