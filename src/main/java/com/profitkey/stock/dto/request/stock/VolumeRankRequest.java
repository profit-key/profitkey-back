package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VolumeRankRequest {
	@Schema(description = "거래ID FHKST01010100 : 주식현재가 시세", example = "FHPST01710000")
	private String tr_id;
	@Schema(description = "고객 타입 P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건 화면 분류 코드", example = "20171")
	private String fidCondScrDivCode;
	@Schema(description = "입력 종목코드", example = "000000")
	private String fidInputJscd;
	@Schema(description = "분류 구분 코드", example = "0")
	private String fidDivClsCode;
	@Schema(description = "소속 구분 코드", example = "2")
	private String fidBlngClsCode;
	@Schema(description = "대상 구분 코드", example = "1111111111")
	private String fidTrgtClsCode;
	@Schema(description = "대상 제외 구분 코드", example = "0000000000")
	private String fidTrgtExclsClsCode;
	@Schema(description = "입력 가격1", example = "0")
	private String fidInputPrice1;
	@Schema(description = "입력 가격2", example = "1000000")
	private String fidInputPrice2;
	@Schema(description = "거래량 수", example = "100000")
	private String fidVolCnt;
	@Schema(description = "입력 날짜1", example = "")
	private String fidInputDate1;

	public VolumeRankRequest(
		String tr_id,
		String custtype,
		String fidCondMrktDivCode,
		String fidCondScrDivCode,
		String fidInputJscd,
		String fidDivClsCode,
		String fidBlngClsCode,
		String fidTrgtClsCode,
		String fidTrgtExclsClsCode,
		String fidInputPrice1,
		String fidInputPrice2,
		String fidVolCnt,
		String fidInputDate1
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidCondScrDivCode = fidCondScrDivCode;
		this.fidInputJscd = fidInputJscd;
		this.fidDivClsCode = fidDivClsCode;
		this.fidBlngClsCode = fidBlngClsCode;
		this.fidTrgtClsCode = fidTrgtClsCode;
		this.fidTrgtExclsClsCode = fidTrgtExclsClsCode;
		this.fidInputPrice1 = fidInputPrice1;
		this.fidInputPrice2 = fidInputPrice2;
		this.fidVolCnt = fidVolCnt;
		this.fidInputDate1 = fidInputDate1;
	}
}
