package com.profitkey.stock.dto.response.faq;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FaqCategoryListResponse {
	private List<FaqCategoryResponse> categoryList;

	@Builder
	public FaqCategoryListResponse(List<FaqCategoryResponse> categoryList) {
		this.categoryList = categoryList;
	}
}
