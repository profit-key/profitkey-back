package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketCapRequest {
	@Schema(description = "거래ID FHKST01010100 : 주식현재가 시세", example = "FHPST01740000")
	private String tr_id;
	@Schema(description = "입력 가격2", example = "1000000")
	private String fidInputPrice2;

	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;

	@Schema(description = "조건 화면 분류 코드", example = "20174")
	private String fidCondScrDivCode;

	@Schema(description = "분류 구분 코드", example = "0")
	private String fidDivClsCode;

	@Schema(description = "입력 종목코드", example = "0000")
	private String fidInputIscd;

	@Schema(description = "대상 구분 코드", example = "0")
	private String fidTrgtClsCode;

	@Schema(description = "대상 제외 구분 코드", example = "0")
	private String fidTrgtExclsClsCode;

	@Schema(description = "입력 가격1", example = "0")
	private String fidInputPrice1;

	@Schema(description = "거래량 수", example = "100000")
	private String fidVolCnt;

	public MarketCapRequest(
		String tr_id,
		String fidInputPrice2,
		String fidCondMrktDivCode,
		String fidCondScrDivCode,
		String fidDivClsCode,
		String fidInputIscd,
		String fidTrgtClsCode,
		String fidTrgtExclsClsCode,
		String fidInputPrice1,
		String fidVolCnt
	) {
		this.tr_id = tr_id;
		this.fidInputPrice2 = fidInputPrice2;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidCondScrDivCode = fidCondScrDivCode;
		this.fidDivClsCode = fidDivClsCode;
		this.fidInputIscd = fidInputIscd;
		this.fidTrgtClsCode = fidTrgtClsCode;
		this.fidTrgtExclsClsCode = fidTrgtExclsClsCode;
		this.fidInputPrice1 = fidInputPrice1;
		this.fidVolCnt = fidVolCnt;
	}
}
