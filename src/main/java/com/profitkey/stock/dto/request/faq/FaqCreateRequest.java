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
	@NotBlank(message = "제목은 필수 입력값입니다")
	@Schema(type = "string", description = "FAQ 제목", example = "제목을 입력해주세요")
	private String title;

	@Schema(type = "string", description = "FAQ 질문", example = "회원 탈퇴는 어떻게 하나요?")
	private String question;

	@Schema(type = "string", description = "FAQ 답변", example = """
		회원탈퇴는 아래 절차를 따라 진행하실 수 있습니다.
		1. PROFITKEY 홈페이지에 로그인합니다.
		...
		""")
	private String answer;

	@Schema(description = "공개여부")
	private Boolean published;
}
