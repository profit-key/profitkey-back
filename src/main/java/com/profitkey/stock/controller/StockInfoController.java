package com.profitkey.stock.controller;

import com.profitkey.stock.dto.response.stock.StockInfoResponse;
import com.profitkey.stock.entity.StockSort;
import com.profitkey.stock.service.stock.StockInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock-info")
@RequiredArgsConstructor
@Slf4j
public class StockInfoController {

	private final StockInfoService stockInfoService;

	/**
	 * 종목순위 Daily 순위 API
	 * @param division
	 * 	BASIC : 기본
	 * 	MARKET_CAP : 시가총액
	 * 	HTS_TOP : HTS TOP 20
	 * @return 종목기본정보 목록
	 */
	@GetMapping("/daily-rank/{division}")
	public ResponseEntity<List<StockInfoResponse>> getDailyRank(@PathVariable StockSort division) {
		return ResponseEntity.ok(stockInfoService.getDailyRank(division));
	}

}
