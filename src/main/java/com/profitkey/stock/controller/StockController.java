package com.profitkey.stock.controller;

import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.service.StockService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

	private final StockService stockService;
	private final KisApiProperties kisApiProperties;

	@GetMapping
	public String getStockData() {
		return stockService.getStockInfo();
	}

	@GetMapping("/getToken")
	public String getToken() throws IOException {
		return stockService.getToken();
	}

	@GetMapping("/getInquirePrice")
	public String getInquirePrice() throws IOException {
		return stockService.getInquirePrice();
	}

}
