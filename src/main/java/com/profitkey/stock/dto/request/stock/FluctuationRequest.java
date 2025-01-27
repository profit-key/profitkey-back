package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FluctuationRequest {
	@Schema(description = "거래ID", example = "FHPST01700000")
	private String tr_id;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "등락 비율2"
		+ "입력값 없을때 전체 (~ 비율", example = "")
	private String fidRsflRate2;
	@Schema(description = "조건 시장 분류 코드"
		+ "시장구분코드 (주식 J)", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "조건 화면 분류 코드"
		+ "Unique key( 20170 )", example = "20170")
	private String fidCondScrDivCode;
	@Schema(description = "입력 종목코드"
		+ "0000(전체) 코스피(0001), 코스닥(1001), 코스피200(2001)", example = "0000")
	private String fidInputIscd;
	@Schema(description = "순위 정렬 구분 코드"
		+ "0:상승율순 1:하락율순 2:시가대비상승율 3:시가대비하락율 4:변동율", example = "0")
	private String fidRankSortClsCode;
	@Schema(description = "입력 수1"
		+ "0:전체 , 누적일수 입력", example = "0")
	private String fidInputCnt1;
	@Schema(description = "가격 구분 코드"
		+ "'fid_rank_sort_cls_code :0 상승율 순일때 (0:저가대비, 1:종가대비)\n"
		+ "fid_rank_sort_cls_code :1 하락율 순일때 (0:고가대비, 1:종가대비)\n"
		+ "fid_rank_sort_cls_code : 기타 (0:전체)'", example = "0")
	private String fidPrcClsCode;
	@Schema(description = "입력 가격1"
		+ "입력값 없을때 전체 (가격 ~)", example = "")
	private String fidInputPrice1;
	@Schema(description = "입력 가격2"
		+ "입력값 없을때 전체 (~ 가격)", example = "")
	private String fidInputPrice2;
	@Schema(description = "거래량 수"
		+ "입력값 없을때 전체 (거래량 ~)\n", example = "")
	private String fidVolCnt;
	@Schema(description = "대상 구분 코드"
		+ "0:전체", example = "0")
	private String fidTrgtClsCode;
	@Schema(description = "대상 제외 구분 코드"
		+ "0:전체", example = "0")
	private String fidTrgtExlsClsCode;
	@Schema(description = "분류 구분 코드"
		+ "0:전체", example = "0")
	private String fidDivClsCode;
	@Schema(description = "등록 비율1"
		+ "입력값 없을때 전체 (비율 ~)", example = "")
	private String fidRsflRate1;

	public FluctuationRequest(
		String tr_id,
		String custtype,
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
		String fidTrgtExlsClsCode,
		String fidDivClsCode,
		String fidRsflRate1
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
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
		this.fidTrgtExlsClsCode = fidTrgtExlsClsCode;
		this.fidDivClsCode = fidDivClsCode;
		this.fidRsflRate1 = fidRsflRate1;
	}
}
