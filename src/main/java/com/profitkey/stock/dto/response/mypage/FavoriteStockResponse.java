package com.profitkey.stock.dto.response.mypage;

import java.math.BigDecimal;
import java.util.Comparator;

import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.entity.StockInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "관심 종목 응답")
public class FavoriteStockResponse {
	private String stockCode;
	private String stockName;
	private Integer fiftyTwoWeekHigh;
	private Integer fiftyTwoWeekLow;
	private BigDecimal pbr;
	private BigDecimal per;
	private Integer eps;
	private Long marketCap;

	@Builder
	public FavoriteStockResponse(String stockCode, String stockName, Integer fiftyTwoWeekHigh, Integer fiftyTwoWeekLow,
		BigDecimal pbr, BigDecimal per, Integer eps, Long marketCap) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
		this.pbr = pbr;
		this.per = per;
		this.eps = eps;
		this.marketCap = marketCap;
	}

	public static FavoriteStockResponse fromEntity(FavoriteStock favoriteStock) {
		StockCode stockCode = favoriteStock.getStockCode();
		StockInfo latestStockInfo = stockCode.getStockInfos().stream()
			.max(Comparator.comparing(StockInfo::getBaseDate))
			.orElse(null); // 최신 날짜의 주식 정보 가져오기

		return new FavoriteStockResponse(
			stockCode.getStockCode(),
			stockCode.getStockName(),
			latestStockInfo != null ? latestStockInfo.getFiftyTwoWeekHigh() : null,
			latestStockInfo != null ? latestStockInfo.getFiftyTwoWeekLow() : null,
			latestStockInfo != null ? latestStockInfo.getPbr() : null,
			latestStockInfo != null ? latestStockInfo.getPer() : null,
			latestStockInfo != null ? latestStockInfo.getEps() : null,
			latestStockInfo != null ? latestStockInfo.getMarketCap() : null
		);
	}

}
