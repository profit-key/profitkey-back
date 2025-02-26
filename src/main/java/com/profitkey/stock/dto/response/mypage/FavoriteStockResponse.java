package com.profitkey.stock.dto.response.mypage;

import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.StockCode;

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
	private boolean isLiked;

	@Builder
	public FavoriteStockResponse(String stockCode, String stockName, boolean isLiked) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.isLiked = isLiked;
	}

	// 엔티티를 DTO로 변환하는 메서드 수정
	public static FavoriteStockResponse fromEntity(FavoriteStock favoriteStock, boolean isLiked) {
		StockCode stockCodeEntity = favoriteStock.getStockCode();

		return FavoriteStockResponse.builder()
			.stockCode(stockCodeEntity.getStockCode())
			.stockName(stockCodeEntity.getStockName())
			.isLiked(isLiked)
			.build();
	}

}
