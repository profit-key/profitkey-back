package com.profitkey.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
	@Column(name = "STOCK_CODE", columnDefinition = "VARCHAR(12)")
	private String stockCode;

	@Column(name = "STOCK_NAME", nullable = false)
	private String stockName;

	@Column(name = "MARKET_CATEGORY", nullable = false)
	private String marketCategory;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<StockInfo> stockInfos;
	
	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<FavoriteStock> favoriteStocks;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<AiAnalysisOpinion> aiAnalysisOpinions;

	@Builder
	private StockCode(String stockCode, String stockName, String marketCategory) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.marketCategory = marketCategory;
	}
} 
