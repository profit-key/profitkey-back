package com.profitkey.stock.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "페이지네이션 스키마")
public class Pagenation {
	private int totalPages;
	private long totalElements;
	private int currentPage;

	@Builder
	public Pagenation(int totalPages, long totalElements, int currentPage) {
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.currentPage = currentPage;
	}
}
