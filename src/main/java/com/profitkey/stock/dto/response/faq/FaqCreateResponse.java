package com.profitkey.stock.dto.response.faq;

import java.util.List;

import com.profitkey.stock.dto.common.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "FAQ 생성 응답")
public class FaqCreateResponse {
	private String faqCategoryName;
	private String title;
	private String content;
	private Boolean published;
	private FileInfo[] fileInfos;

	@Builder
	public FaqCreateResponse(String faqCategoryName, String title, String content, Boolean published,
		FileInfo[] fileInfos) {
		this.faqCategoryName = faqCategoryName;
		this.title = title;
		this.content = content;
		this.published = published;
		this.fileInfos = fileInfos;
	}
}
