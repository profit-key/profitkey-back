package com.profitkey.stock.dto.response.faq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "카테고리별 FAQ 조회 응답")
public class FaqListResponse {
	private FaqListItemResponse[] faqList;
	private int totalPages;        // 전체 페이지 수
	private long totalElements;    // 전체 항목 수
	private int currentPage;       // 현재 페이지

	@Builder
	public FaqListResponse(FaqListItemResponse[] faqList, int totalPages, long totalElements, int currentPage) {
		this.faqList = faqList;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.currentPage = currentPage;
	}
}
