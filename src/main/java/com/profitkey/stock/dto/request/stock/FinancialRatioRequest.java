package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FinancialRatioRequest {
	@Schema(description = "거래 ID", example = "FHKST66430300")
	private String tr_id;
	@Schema(description = "고객 타입 (B: 법인, P: 개인)", example = "P")
	private String custtype;
	@Schema(description = "분류 구분 코드", example = "0")
	private String fidDivClsCode;
	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "입력 종목코드", example = "000660")
	private String fidInputIscd;

	public FinancialRatioRequest(
		String trId,
		String custtype,
		String fidDivClsCode,
		String fidCondMrktDivCode,
		String fidInputIscd
	) {
		this.tr_id = trId;
		this.custtype = custtype;
		this.fidDivClsCode = fidDivClsCode;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidInputIscd = fidInputIscd;
	}
}
