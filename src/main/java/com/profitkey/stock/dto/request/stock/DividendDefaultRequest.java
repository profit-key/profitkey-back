package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class DividendDefaultRequest extends DividendRequest {
	public DividendDefaultRequest(String shtCd, String fromDate, String toDate) {
		super(
			"HHKDB669102C0", // tr_id
			"P", // custtype
			"", // cts
			"0", // gb1
			fromDate,
			toDate,
			shtCd, // shtCd
			"" // highGb
		);
	}
}
