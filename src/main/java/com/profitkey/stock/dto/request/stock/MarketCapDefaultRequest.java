package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class MarketCapDefaultRequest extends MarketCapRequest {
	public MarketCapDefaultRequest() {
		super(
			"FHPST01740000", // tr_id
			"P", // custtype
			"", // fidInputPrice2
			"J", // fidCondMrktDivCode
			"20174", // fidCondScrDivCode
			"0", // fidDivClsCode
			"0000", // fidInputIscd
			"0", // fidTrgtClsCode
			"0", // fidTrgtExlsClsCode
			"", // fidInputPrice1
			""  // fidVolCnt
		);
	}
}
