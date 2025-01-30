package com.profitkey.stock.dto.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityRequest {
	@Schema(description = "종목코드", example = "123456")
	private String stockCode;
	@Schema(description = "작성자 식별자", example = "0")
	private Integer writerId;
	@Schema(description = "부모글 번호 / root 일경우 0", example = "0")
	private String parentId;
	@Schema(description = "내용", example = "코인하세요")
	private String content;
}
