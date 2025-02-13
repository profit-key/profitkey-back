package com.profitkey.stock.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "페이지네이션 요청")
public class PaginationRequest {

	@Schema(description = "페이지 번호", example = "1", required = true)
	private int page = 1;

	@Schema(description = "페이지 크기", example = "10", required = true)
	private int size = 10;

	public PaginationRequest(int page, int size) {
		this.page = page;
		this.size = size;
	}
}
