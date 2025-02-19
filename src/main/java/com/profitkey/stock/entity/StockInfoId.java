// package com.profitkey.stock.entity;
//
// import jakarta.persistence.Embeddable;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import java.io.Serializable;
// import lombok.AllArgsConstructor;
// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Embeddable
// @Getter
// @NoArgsConstructor
// @AllArgsConstructor
// @EqualsAndHashCode
// public class StockInfoId implements Serializable {
// 	@ManyToOne
// 	@JoinColumn(name = "STOCK_CODE")
// 	private StockCode stockCode;
// 	private String baseDate;
//
// 	public void setStockCode(StockCode stockCode) {
// 		this.stockCode = stockCode;
// 	}
// }