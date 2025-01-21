package com.profitkey.stock.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "StockCodes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockCode {
	@Id
	@Column(name = "stock_code", columnDefinition = "VARCHAR(12)")
	private String stockCode;

	@Column(name = "stock_name", nullable = false)
	private String stockName;

	@Column(name = "market_category", nullable = false)
	private String marketCategory;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	private List<StockInfo> stockInfos;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	private List<FavoriteStock> favoriteStocks;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	private List<AiAnalysisOpinion> aiAnalysisOpinions;

	@Builder
	private StockCode(String stockCode, String stockName, String marketCategory) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.marketCategory = marketCategory;
	}
} 
