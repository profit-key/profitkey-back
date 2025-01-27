package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketCapRequest {
	@Schema(description = "거래ID", example = "FHPST01740000")
	private String tr_id;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "입력 가격2"
		+ "입력값 없을때 전체 (~ 가격)", example = "")
	private String fidInputPrice2;
	@Schema(description = "조건 시장 분류 코드"
		+ "시장구분코드 (주식 J)", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건 화면 분류 코드"
		+ "Unique key( 20174 )", example = "20174")
	private String fidCondScrDivCode;
	@Schema(description = "분류 구분 코드"
		+ "0: 전체, 1:보통주, 2:우선주", example = "0")
	private String fidDivClsCode;
	@Schema(description = "입력 종목코드"
		+ "0000:전체, 0001:거래소, 1001:코스닥, 2001:코스피200", example = "0000")
	private String fidInputIscd;
	@Schema(description = "대상 구분 코드"
		+ "0 : 전체", example = "0")
	private String fidTrgtClsCode;
	@Schema(description = "대상 제외 구분 코드"
		+ "0 : 전체", example = "0")
	private String fidTrgtExlsClsCode;
	@Schema(description = "입력 가격1"
		+ "입력값 없을때 전체 (가격 ~)", example = "")
	private String fidInputPrice1;
	@Schema(description = "거래량 수"
		+ "입력값 없을때 전체 (거래량 ~)", example = "")
	private String fidVolCnt;

	public MarketCapRequest(
		String tr_id,
		String custtype,
		String fidInputPrice2,
		String fidCondMrktDivCode,
		String fidCondScrDivCode,
		String fidDivClsCode,
		String fidInputIscd,
		String fidTrgtClsCode,
		String fidTrgtExlsClsCode,
		String fidInputPrice1,
		String fidVolCnt
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
		this.fidInputPrice2 = fidInputPrice2;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidCondScrDivCode = fidCondScrDivCode;
		this.fidDivClsCode = fidDivClsCode;
		this.fidInputIscd = fidInputIscd;
		this.fidTrgtClsCode = fidTrgtClsCode;
		this.fidTrgtExlsClsCode = fidTrgtExlsClsCode;
		this.fidInputPrice1 = fidInputPrice1;
		this.fidVolCnt = fidVolCnt;
	}
}
