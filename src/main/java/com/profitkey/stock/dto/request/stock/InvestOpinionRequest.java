package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvestOpinionRequest {
	@Schema(description = "거래 ID", example = "FHKST663300C0")
	private String tr_id;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "조시징구분코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건화면구분코드", example = "16633")
	private String fidCondScrDivCode;
	@Schema(description = "입력종목코드", example = "005930")
	private String fidInputIscd;
	@Schema(description = "입력날짜1", example = "0020231113")
	private String fidInputDate1;
	@Schema(description = "입력날짜2", example = "0020240513")
	private String fidInputDate2;

	public InvestOpinionRequest(
		String tr_id,
		String custtype,
		String fidCondMrktDivCode,
		String fidCondScrDivCode,
		String fidInputIscd,
		String fidInputDate1,
		String fidInputDate2
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidCondScrDivCode = fidCondScrDivCode;
		this.fidInputIscd = fidInputIscd;
		this.fidInputDate1 = fidInputDate1;
		this.fidInputDate2 = fidInputDate2;
	}
}
