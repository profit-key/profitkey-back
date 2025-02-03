package com.profitkey.stock.dto.request.faq;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqCategoryCreateRequest {
    private String categoryName;
    private Integer displayOrder;
    private Boolean isUse;
}