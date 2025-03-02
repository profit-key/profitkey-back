package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HtsTopViewRequest {
	@Schema(description = "거래ID", example = "HHMCM000100C0")
	private String tr_id;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;

	public HtsTopViewRequest(
		String tr_id,
		String custtype
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
	}
}
