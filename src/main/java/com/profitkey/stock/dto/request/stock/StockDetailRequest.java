package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockDetailRequest {
	@Schema(description = "FID 조건 시장 분류 코드 J : 주식, ETF, ETN", example = "J")
	private String mrktDivCode;
	@Schema(description = "FID 입력 종목코드", example = "005930")
	private String fidInput;
	@Schema(description = "고객 타입 P: 개인, B: 법인", example = "P")
	private String custtype;

	@Schema(description = "CTS", example = "")
	private String cts;
	@Schema(description = "조회구분", example = "0")
	private String gb1;
	@Schema(description = "조회일자 From", example = "20241031")
	private String fdt;
	@Schema(description = "조회일자 To", example = "20241231")
	private String tdt;
	@Schema(description = "고배당여부", example = "")
	private String highGb;

	public StockDetailRequest(
		String mrktDivCode,
		String custtype,
		String fidInput,
		String cts,
		String gb1,
		String fdt,
		String tdt,
		String highGb
	) {
		this.mrktDivCode = mrktDivCode;
		this.fidInput = fidInput;
		this.custtype = custtype;
		this.cts = cts;
		this.gb1 = gb1;
		this.fdt = fdt;
		this.tdt = tdt;
		this.highGb = highGb;
	}
}
