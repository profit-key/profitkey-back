package com.profitkey.stock.dto.request.stock;

import lombok.Getter;

@Getter
public class GrowthRatioDefaultRequest extends GrowthRatioRequest {
	public GrowthRatioDefaultRequest(String fidInputIscd) {
		super(
			"FHKST66430800",
			"P",
			fidInputIscd,
			"0",
			"J"
		);
	}
}
