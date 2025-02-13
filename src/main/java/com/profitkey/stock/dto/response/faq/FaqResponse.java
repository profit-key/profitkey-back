package com.profitkey.stock.dto.response.faq;

import java.time.LocalDateTime;

import com.profitkey.stock.dto.common.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "FAQ 상세 조회 응답")
public class FaqResponse {
    private Long id;
    private String question;
    private String answer;
    private Boolean published;
    private LocalDateTime createdAt;

    @Builder
    public FaqResponse(Long id, String title, String question, String answer, Boolean published, LocalDateTime createdAt, FileInfo[] fileInfos) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.published = published;
        this.createdAt = createdAt;
    }
}
