package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfitRatioRequest {
	@Schema(description = "거래 ID", example = "FHKST66430400")
	private String tr_id;
	@Schema(description = "입력 종목코드", example = "000660")
	private String fidInputIscd;
	@Schema(description = "분류 구분 코드", example = "0")
	private String fidDivClsCode;
	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;

	public ProfitRatioRequest(
		String trId,
		String fidInputIscd,
		String fidDivClsCode,
		String fidCondMrktDivCode
	) {
		this.tr_id = trId;
		this.fidInputIscd = fidInputIscd;
		this.fidDivClsCode = fidDivClsCode;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
	}
}
