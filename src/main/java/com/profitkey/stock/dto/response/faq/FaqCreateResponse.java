package com.profitkey.stock.dto.response.faq;

import java.util.List;
import java.util.UUID;

import com.profitkey.stock.dto.common.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "FAQ 생성 응답")
public class FaqCreateResponse {
	private Long id;
	private String title;
	private String question;
	private String answer;
	private Boolean published;

	@Builder
	public FaqCreateResponse(Long id, String faqCategoryName, String title, String question, String answer, Boolean published) {
		this.id = id;
		this.title = title;
		this.question = question;
		this.answer = answer;
		this.published = published;
	}
}
