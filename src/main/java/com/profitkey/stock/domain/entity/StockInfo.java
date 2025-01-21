package com.profitkey.stock.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "StockInfos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(StockInfoId.class)
public class StockInfo {
	@Id
	@ManyToOne()
	@JoinColumn(name = "stock_code", nullable = false)
	private StockCode stockCode;

	@Id
	@Column(name = "base_dt", nullable = false, length = 8)
	private String baseDate;

	@Column(name = "ending_price", nullable = false, precision = 12, scale = 0)
	private BigDecimal endingPrice;

	@Column(name = "opening_price", nullable = false)
	private Integer openingPrice;

	@Column(name = "high_price", nullable = false)
	private Integer highPrice;

	@Column(name = "low_price", nullable = false)
	private Integer lowPrice;

	@Column(name = "trading_volume", nullable = false)
	private Long tradingVolume;

	@Column(name = "trading_value", nullable = false)
	private Long tradingValue;

	@Column(name = "market_cap", nullable = false)
	private Long marketCap;

	@Column(name = "fifty_two_week_high", nullable = false)
	private Integer fiftyTwoWeekHigh;

	@Column(name = "fifty_two_week_low", nullable = false)
	private Integer fiftyTwoWeekLow;

	@Column(name = "per", precision = 5, scale = 2, nullable = false)
	private BigDecimal per;

	@Column(name = "eps", nullable = false)
	private Integer eps;

	@Column(name = "pbr", precision = 5, scale = 2, nullable = false)
	private BigDecimal pbr;

	@Column(name = "bps", nullable = false)
	private Integer bps;

	@Builder
	private StockInfo(StockCode stockCode, String baseDate, BigDecimal endingPrice, Integer openingPrice,
		Integer highPrice, Integer lowPrice, Long tradingVolume, Long tradingValue, Long marketCap,
		Integer fiftyTwoWeekHigh, Integer fiftyTwoWeekLow, BigDecimal per, Integer eps, BigDecimal pbr, Integer bps) {
		this.stockCode = stockCode;
		this.baseDate = baseDate;
		this.endingPrice = endingPrice;
		this.openingPrice = openingPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.tradingVolume = tradingVolume;
		this.tradingValue = tradingValue;
		this.marketCap = marketCap;
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
		this.per = per;
		this.eps = eps;
		this.pbr = pbr;
		this.bps = bps;
	}
} 