package com.moamen.store.service;

import com.moamen.store.dto.PromotionDto;

public interface PromotionService {

    PromotionDto findByPromotionCode(String promotionCode);
}

