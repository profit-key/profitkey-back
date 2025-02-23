package com.profitkey.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "StockInfos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(StockInfoId.class)
@AllArgsConstructor
@Builder
public class StockInfo {
	@Id
	@ManyToOne()
	@JoinColumn(name = "STOCK_CODE", nullable = false)
	@JsonIgnore
	private StockCode stockCode;

	@Id
	@Column(name = "BASE_DATE", nullable = false, length = 8)
	private String baseDate;

	@Column(name = "ENDING_PRICE", nullable = false, precision = 12, scale = 0)
	private BigDecimal endingPrice;

	@Column(name = "OPENING_PRICE", nullable = false)
	private Integer openingPrice;

	@Column(name = "HIGH_PRICE", nullable = false)
	private Integer highPrice;

	@Column(name = "LOW_PRICE", nullable = false)
	private Integer lowPrice;

	@Column(name = "TRADING_VOLUME", nullable = false)
	private Long tradingVolume;

	@Column(name = "TRADING_VALUE", nullable = false)
	private Long tradingValue;

	@Column(name = "MARKET_CAP", nullable = false)
	private Long marketCap;

	@Column(name = "FIFTY_TWO_WEEK_HIGH", nullable = false)
	private Integer fiftyTwoWeekHigh;

	@Column(name = "FIFTY_TWO_WEEK_LOW", nullable = false)
	private Integer fiftyTwoWeekLow;

	@Column(name = "PER", precision = 5, scale = 2, nullable = false)
	private BigDecimal per;

	@Column(name = "EPS", nullable = false)
	private BigDecimal eps;

	@Column(name = "PBR", precision = 5, scale = 2, nullable = false)
	private BigDecimal pbr;

	@Column(name = "BPS", nullable = false)
	private BigDecimal bps;

}