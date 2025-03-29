package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class StabilityRatioDefaultRequest extends StabilityRatioRequest {

	public StabilityRatioDefaultRequest(String fidInputIscd) {
		super(
			"FHKST66430600",
			"P",
			fidInputIscd,
			"0",
			"J"

		);
	}

}
