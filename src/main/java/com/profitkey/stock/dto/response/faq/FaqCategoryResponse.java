package com.profitkey.stock.dto.response.faq;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FaqCategoryResponse {
	private Long categoryId;
	private String categoryName;
	private Integer displayOrder;
	private Boolean isUse;

	public static FaqCategoryResponse of(Long categoryId, String categoryName, Integer displayOrder, Boolean isUse) {
        FaqCategoryResponse response = new FaqCategoryResponse();
        response.categoryId = categoryId;
        response.categoryName = categoryName;
        response.displayOrder = displayOrder;
        response.isUse = isUse;
        return response;
    }
}
