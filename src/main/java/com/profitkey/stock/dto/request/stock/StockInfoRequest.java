package com.profitkey.stock.dto.request.stock;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class StockInfoRequest {
	private String stockCode;
	private String baseDate;
	private BigDecimal endingPrice;
	private Integer openingPrice;
	private Integer highPrice;
	private Integer lowPrice;
	private Long tradingVolume;
	private Long tradingValue;
	private Long marketCap;
	private Integer fiftyTwoWeekHigh;
	private Integer fiftyTwoWeekLow;
	private BigDecimal per;
	private Integer eps;
	private BigDecimal pbr;
	private Integer bps;
}
