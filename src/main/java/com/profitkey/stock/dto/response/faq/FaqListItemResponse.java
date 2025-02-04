package com.profitkey.stock.dto.response.faq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqListItemResponse {
	private Long id;
	private String title;
	private String question;

	@Builder
	public FaqListItemResponse(Long id, String title, String question) {
		this.id = id;
		this.title = title;
		this.question = question;
	}
}