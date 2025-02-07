package com.profitkey.stock.dto.response.faq;

import com.profitkey.stock.dto.common.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "FAQ 목록 조회 응답")
public class FaqListResponse {
	private FaqListItemResponse[] faqList;
	private Pagination pagination;

	@Builder
	public FaqListResponse(FaqListItemResponse[] faqList, Pagination pagination) {
		this.faqList = faqList;
		this.pagination = pagination;
	}
}
