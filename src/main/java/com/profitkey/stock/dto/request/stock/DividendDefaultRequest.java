package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class DividendDefaultRequest extends DividendRequest {
	public DividendDefaultRequest(String shtCd) {
		super(
			"HHKDB669102C0", // tr_id
			"P", // custtype
			"", // cts
			"0", // gb1
			"20241031", // fdt
			"20241231", // tdt
			shtCd, // shtCd
			"" // highGb
		);
	}
}
