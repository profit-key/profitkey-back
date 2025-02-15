package com.profitkey.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FavoriteStocks")
@IdClass(FavoriteStockId.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FavoriteStock {
	@Id
	@ManyToOne()
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserInfo user;

	@Id
	@ManyToOne
	@JoinColumn(name = "STOCK_CODE", columnDefinition = "VARCHAR(12)")
	private StockCode stockCode;
} 