package com.moamen.store.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionDto {


    Long id;

    String code;

    BookTypeDto bookTypeId;

    Double discountPercent;


}
