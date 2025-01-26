package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FluctuationRequest {
	@Schema(description = "거래ID FHKST01010100 : 주식현재가 시세", example = "FHPST01700000")
	private String tr_id;
	@Schema(description = "등락 비율2", example = "")
	private String fidRsflRate2;
	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건 화면 분류 코드", example = "20170")
	private String fidCondScrDivCode;
	@Schema(description = "입력 종목코드", example = "0000")
	private String fidInputIscd;
	@Schema(description = "순위 정렬 구분 코드", example = "0")
	private String fidRankSortClsCode;
	@Schema(description = "입력 수1", example = "0")
	private String fidInputCnt1;
	@Schema(description = "가격 구분 코드", example = "0")
	private String fidPrcClsCode;
	@Schema(description = "입력 가격1", example = "0")
	private String fidInputPrice1;
	@Schema(description = "입력 가격2", example = "1000000")
	private String fidInputPrice2;
	@Schema(description = "거래량 수", example = "100000")
	private String fidVolCnt;
	@Schema(description = "대상 구분 코드", example = "0")
	private String fidTrgtClsCode;
	@Schema(description = "대상 제외 구분 코드", example = "0")
	private String fidTrgtExclsClsCode;
	@Schema(description = "분류 구분 코드", example = "0")
	private String fidDivClsCode;
	@Schema(description = "등록 비율1", example = "")
	private String fidRsflRate1;

	public FluctuationRequest(
		String tr_id,
		String fidRsflRate2,
		String fidCondMrktDivCode,
		String fidCondScrDivCode,
		String fidInputIscd,
		String fidRankSortClsCode,
		String fidInputCnt1,
		String fidPrcClsCode,
		String fidInputPrice1,
		String fidInputPrice2,
		String fidVolCnt,
		String fidTrgtClsCode,
		String fidTrgtExclsClsCode,
		String fidDivClsCode,
		String fidRsflRate1
	) {
		this.tr_id = tr_id;
		this.fidRsflRate2 = fidRsflRate2;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidCondScrDivCode = fidCondScrDivCode;
		this.fidInputIscd = fidInputIscd;
		this.fidRankSortClsCode = fidRankSortClsCode;
		this.fidInputCnt1 = fidInputCnt1;
		this.fidPrcClsCode = fidPrcClsCode;
		this.fidInputPrice1 = fidInputPrice1;
		this.fidInputPrice2 = fidInputPrice2;
		this.fidVolCnt = fidVolCnt;
		this.fidTrgtClsCode = fidTrgtClsCode;
		this.fidTrgtExclsClsCode = fidTrgtExclsClsCode;
		this.fidDivClsCode = fidDivClsCode;
		this.fidRsflRate1 = fidRsflRate1;
	}
}
