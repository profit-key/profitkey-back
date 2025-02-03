package com.profitkey.stock.dto.request.faq;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "FAQ 생성 요청")
public class FaqCreateRequest {
	@Schema(format = "binary", description = "업로드할 파일", nullable = true)
	private MultipartFile[] files;

	@NotBlank(message = "카테고리는 필수 입력값입니다")
	@Schema(type = "string", description = "FAQ카테고리 이름", example = "카테고리를 입력해주세요.")
	private String faqCategoryName;

	@Schema(description = "공개여부")
	private Boolean published;

	@NotBlank(message = "제목은 필수 입력값입니다")
	@Schema(type = "string", description = "FAQ 제목", example = "제목을 입력해주세요")
	private String title;

	@Schema(type = "string", description = "FAQ 내용", example = "내용을 입력해주세요", nullable = true)
	private String content;
}
