package com.profitkey.stock.dto.response.faq;

import java.time.LocalDateTime;

import com.profitkey.stock.dto.common.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "FAQ 상세 조회 응답")
public class FaqResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean published;
    private String categoryName;
    private LocalDateTime createdAt;
    private FileInfo[] fileInfos;

    @Builder
    public FaqResponse(Long id, String title, String content, Boolean published, String categoryName, LocalDateTime createdAt, FileInfo[] fileInfos) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.fileInfos = fileInfos;
    }
}
