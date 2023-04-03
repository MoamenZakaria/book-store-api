package com.moamen.store.service.impl;

import com.moamen.store.dto.PromotionDto;
import com.moamen.store.mapper.PromotionMapper;
import com.moamen.store.model.Promotion;
import com.moamen.store.repository.PromotionRepository;
import com.moamen.store.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    @Override
    public PromotionDto findByPromotionCode(String promotionCode) {
        final Promotion promotion = promotionRepository.findByCode(promotionCode);
        return promotionMapper.toDto(promotion);
    }
}
