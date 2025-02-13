package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DividendRequest {
	@Schema(description = "거래 ID", example = "HHKDB669102C0")
	private String tr_id;
	@Schema(description = "고객 타입 (B: 법인, P: 개인)", example = "P")
	private String custtype;
	@Schema(description = "CTS", example = "")
	private String cts;
	@Schema(description = "조회구분", example = "0")
	private String gb1;
	@Schema(description = "조회일자 From", example = "20241031")
	private String fdt;
	@Schema(description = "조회일자 To", example = "20241231")
	private String tdt;
	@Schema(description = "종목코드"
		+ "공백: 전체, 특정종목 조회시 : 종목코드", example = "005930")
	private String shtCd;
	@Schema(description = "고배당여부", example = "")
	private String highGb;

	public DividendRequest(
		String trId,
		String custtype,
		String cts,
		String gb1,
		String fdt,
		String tdt,
		String shtCd,
		String highGb
	) {
		this.tr_id = trId;
		this.custtype = custtype;
		this.cts = cts;
		this.gb1 = gb1;
		this.fdt = fdt;
		this.tdt = tdt;
		this.shtCd = shtCd;
		this.highGb = highGb;
	}
}
