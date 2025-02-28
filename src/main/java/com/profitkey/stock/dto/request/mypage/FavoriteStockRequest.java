package com.profitkey.stock.dto.request.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "관심 종목 추가 요청")
public class FavoriteStockRequest {

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "주식 코드", example = "005930")
	private String stockCode;
}
