package com.profitkey.stock.controller;

import com.profitkey.stock.dto.response.stock.StockInfoResponse;
import com.profitkey.stock.service.stock.StockInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock-info")
@RequiredArgsConstructor
@Slf4j
public class StockInfoController {

	private final StockInfoService stockInfoService;

	/**
	 * 시가총액기준 Daily 순위 API
	 * @param
	 * @return 종목기본정보 목록
	 */
	@GetMapping("/market-cap")
	public ResponseEntity<List<StockInfoResponse>> getMarketCap() {
		return ResponseEntity.ok(stockInfoService.getMarketCap());
	}
}
