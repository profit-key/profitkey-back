package com.profitkey.stock.dto;

public enum ApiUrl {
	// 토큰P
	STOCK_KIS_OAUTH2_TOKEN_URL("https://api.koreainvestment.com/stock-price"),
	// 주식시세
	STOCK_KIS_INQUIRE_PRICE_URL("https://api.koreainvestment.com/order");

	private final String url;

	ApiUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
