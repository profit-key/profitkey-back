package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VolumeRankRequest {
	@Schema(description = "거래ID", example = "FHPST01710000")
	private String tr_id;
	@Schema(description = "조건 시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건 화면 분류 코드", example = "20171")
	private String fidCondScrDivCode;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "입력 종목코드"
		+ "0000(전체) 기타(업종코드)", example = "0000")
	private String fidInputJscd;
	@Schema(description = "분류 구분 코드"
		+ "0(전체) 1(보통주) 2(우선주)", example = "0")
	private String fidDivClsCode;
	@Schema(description = "소속 구분 코드"
		+ "0 : 평균거래량 1:거래증가율 2:평균거래회전율 3:거래금액순 4:평균거래금액회전율", example = "2")
	private String fidBlngClsCode;
	@Schema(description = "대상 구분 코드"
		+ "1 or 0 9자리 (차례대로 증거금 30% 40% 50% 60% 100% 신용보증금 30% 40% 50% 60%)", example = "1111111111")
	private String fidTrgtClsCode;
	@Schema(description = "대상 제외 구분 코드"
		+ "1 or 0 10자리 (차례대로 투자위험/경고/주의 관리종목 정리매매 불성실공시 우선주 거래정지 ETF ETN 신용주문불가 SPAC)", example = "0000000000")
	private String fidTrgtExlsClsCode;
	@Schema(description = "입력 가격1"
		+ "전체 가격 대상 조회 시 FID_INPUT_PRICE_1, FID_INPUT_PRICE_2 모두 \"\"(공란) 입력", example = "")
	private String fidInputPrice1;
	@Schema(description = "입력 가격2"
		+ "전체 가격 대상 조회 시 FID_INPUT_PRICE_1, FID_INPUT_PRICE_2 모두 \"\"(공란) 입력", example = "")
	private String fidInputPrice2;
	@Schema(description = "거래량 수"
		+ "전체 거래량 대상 조회 시 FID_VOL_CNT \"\"(공란) 입력", example = "")
	private String fidVolCnt;
	@Schema(description = "입력 날짜1"
		+ "\"\"(공란) 입력", example = "")
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
		String fidTrgtExlsClsCode,
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
		this.fidTrgtExlsClsCode = fidTrgtExlsClsCode;
		this.fidInputPrice1 = fidInputPrice1;
		this.fidInputPrice2 = fidInputPrice2;
		this.fidVolCnt = fidVolCnt;
		this.fidInputDate1 = fidInputDate1;
	}
}
