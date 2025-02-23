package com.profitkey.stock.dto.response.stock;

import com.profitkey.stock.entity.StockInfo;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockInfoResponse {
	private String stockCode;
	private String stockName;
	private String baseDate;
	private BigDecimal endingPrice;
	private long openingPrice;
	private long highPrice;
	private long lowPrice;
	private long tradingVolume;
	private long tradingValue;
	private long marketCap;
	private long fiftyTwoWeekHigh;
	private long fiftyTwoWeekLow;
	private BigDecimal per;
	private BigDecimal eps;
	private BigDecimal pbr;
	private BigDecimal bps;

	public StockInfoResponse(StockInfo stockInfo) {
		this.stockCode = stockInfo.getStockCode().getStockCode();
		this.stockName = stockInfo.getStockCode().getStockName();
		this.baseDate = stockInfo.getBaseDate();
		this.endingPrice = stockInfo.getEndingPrice();
		this.openingPrice = stockInfo.getOpeningPrice();
		this.highPrice = stockInfo.getHighPrice();
		this.lowPrice = stockInfo.getLowPrice();
		this.tradingVolume = stockInfo.getTradingVolume();
		this.tradingValue = stockInfo.getTradingValue();
		this.marketCap = stockInfo.getMarketCap();
		this.fiftyTwoWeekHigh = stockInfo.getFiftyTwoWeekHigh();
		this.fiftyTwoWeekLow = stockInfo.getFiftyTwoWeekLow();
		this.per = stockInfo.getPer();
		this.eps = stockInfo.getEps();
		this.pbr = stockInfo.getPbr();
		this.bps = stockInfo.getBps();
	}
}
