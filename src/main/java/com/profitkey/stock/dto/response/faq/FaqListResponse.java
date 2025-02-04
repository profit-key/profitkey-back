package com.profitkey.stock.dto.response.faq;

import java.time.LocalDateTime;
import java.util.List;

import com.profitkey.stock.dto.common.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "FAQ 목록 조회 응답")
public class FaqListResponse {
	private List<FaqResponse> faqList;
}
