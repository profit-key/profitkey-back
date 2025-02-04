package com.profitkey.stock.dto.response.faq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Schema(description = "FAQ 제목 조회 응답")
public class FaqTitleResponse extends FaqListResponse {
    @SuperBuilder
    public FaqTitleResponse(Long id, String title) {
        super(id, title, null, null, null, null, null);
    }
} 