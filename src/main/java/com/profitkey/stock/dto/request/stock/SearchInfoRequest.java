package com.profitkey.stock.dto.request.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchInfoRequest {
	@Schema(description = "거래 ID", example = "CTPF1604R")
	private String tr_id;
	@Schema(description = "고객 타입"
		+ "P: 개인, B: 법인", example = "P")
	private String custtype;
	@Schema(description = "상품번호", example = "000660")
	private String pdno;
	@Schema(description = "상품유형코드", example = "300")
	private String prdtTypeCd;

	public SearchInfoRequest(
		String tr_id,
		String custtype,
		String pdno,
		String prdtTypeCd
	) {
		this.tr_id = tr_id;
		this.custtype = custtype;
		this.pdno = pdno;
		this.prdtTypeCd = prdtTypeCd;
	}
}
