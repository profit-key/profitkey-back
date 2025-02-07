package com.profitkey.stock.handler;

import org.springframework.stereotype.Component;

import com.profitkey.stock.dto.common.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagenationHandler {

	public Pagination setPagenation(int totalPages, long totalElements, int currentPage) {
		Pagination pagination = Pagination.builder()
			.totalElements(totalElements)
			.totalPages(totalPages)
			.currentPage(currentPage)
			.build();
		return pagination;
	}
}
