package com.profitkey.stock.dto;

public enum InvestmentOpinion {
	BUY("매수"),
	HOLD("보유"),
	SELL("매도"),
	STRONG_BUY("적극 매수"),
	UNDERPERFORM("비추천");

	private final String korean;

	InvestmentOpinion(String korean) {
		this.korean = korean;
	}

	public String getKorean() {
		return korean;
	}

	public static String translate(String koreanOpinion) {
		for (InvestmentOpinion opinion : values()) {
			if (opinion.getKorean().equals(koreanOpinion)) {
				return opinion.name();
			}
		}
		return koreanOpinion;
	}
}

