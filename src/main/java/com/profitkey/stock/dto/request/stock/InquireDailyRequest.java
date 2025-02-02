package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquireDailyRequest {
	@Schema(description = "거래ID", example = "FHKST03010100")
	private String tr_id;
	@Schema(description = "시장 분류 코드", example = "J")
	private String fidCondMrktDivCode;
	@Schema(description = "종목코드", example = "Q500001")
	private String fidInputIscd;
	@Schema(description = "입력 날짜 (시작)"
		+ "조회 시작일자 (ex. 20220501)", example = "20241201")
	private String fidInputDate1;
	@Schema(description = "입력 날짜 (종료)"
		+ "조회 종료일자 (ex. 20220530)"
		+ "ㅁ FID_INPUT_DATE_2 가 현재일 까지일때"
		+ ". 주봉 조회 : 해당 주의 첫번째 영업일이 포함되어야함"
		+ ". 월봉 조회 : 해당 월의 전월 일자로 시작되어야함"
		+ ". 년봉 조회 : 해당 년의 전년도 일자로 시작되어야함"
		+ "ㅁ FID_INPUT_DATE_2 가 현재일보다 이전일 때"
		+ ". 주봉 조회 : 해당 주의 첫번째 영업일이 포함되어야함"
		+ " 월봉 조회 : 해당 월의 영업일이 포함되어야함"
		+ ". 년봉 조회 : 해당 년의 영업일이 포함되어야함", example = "20241231")
	private String fidInputDate2;
	@Schema(description = "기간분류코드"
		+ "D:일봉, W:주봉, M:월봉, Y:년봉", example = "D")
	private String fidPeriodDivCode;
	@Schema(description = "수정주가 원주가 가격 여부"
		+ "0:수정주가 1:원주가", example = "0")
	private String fidOrgAdjPrc;

	public InquireDailyRequest(
		String tr_id,
		String fidCondMrktDivCode,
		String fidInputIscd,
		String fidInputDate1,
		String fidInputDate2,
		String fidPeriodDivCode,
		String fidOrgAdjPrc
	) {
		this.tr_id = tr_id;
		this.fidCondMrktDivCode = fidCondMrktDivCode;
		this.fidInputIscd = fidInputIscd;
		this.fidInputDate1 = fidInputDate1;
		this.fidInputDate2 = fidInputDate2;
		this.fidPeriodDivCode = fidPeriodDivCode;
		this.fidOrgAdjPrc = fidOrgAdjPrc;
	}

}
