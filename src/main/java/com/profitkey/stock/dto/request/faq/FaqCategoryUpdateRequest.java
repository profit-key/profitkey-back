package com.profitkey.stock.dto.request.faq;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FaqCategoryUpdateRequest {
    private Long categoryId;
    private String categoryName;
    private Integer displayOrder;
    private Boolean isUse;
}
