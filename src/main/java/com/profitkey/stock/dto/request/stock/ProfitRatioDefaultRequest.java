package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class ProfitRatioDefaultRequest extends ProfitRatioRequest {
	public ProfitRatioDefaultRequest(String fidInputIscd) {
		super(
			"FHKST66430400",
			fidInputIscd,
			"0",
			"J"
		);
	}
}
