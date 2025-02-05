package com.profitkey.stock.dto.request.announcement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "공지사항 생성 요청")
public class AnnouncementCreateRequest {
	@NotBlank(message = "제목은 필수 입력값입니다")
	@Schema(type = "string", description = "공지사항 제목", example = "제목을 입력해주세요")
	private String title;

	@Schema(type = "string", description = "공지사항 내용", example = """
		공지사항입니다아~~~
		""")
	private String content;

	@Schema(description = "공개여부")
	private Boolean published;
}
