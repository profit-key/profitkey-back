package com.profitkey.stock.dto.response.faq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "카테고리별 FAQ 조회 응답")
public class FaqListResponse {
	private FaqResponse[] faqList;
}
