package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

	private final StockService stockService;
	private final KisApiProperties kisApiProperties;

	// @GetMapping
	// public String getStockData() {
	// 	return stockService.getStockInfo();
	// }

	/**
	 * OAuth인증
	 * 접근토큰발급(P)[인증-001]
	 * * 1분에 하나씩 발급가능
	 * @param
	 * @return access_token
	 */
	@Operation(summary = SwaggerDocs.SUMMARY_STOCK_TOKEN,
		description = SwaggerDocs.DESCRIPTION_STOCK_TOKEN)
	// @ApiErrorExceptions(BoardExceptionDocs.class)
	@GetMapping("/getToken")
	public String getToken() throws IOException {
		return stockService.getToken();
	}

	/**
	 * [국내주식]기본시세 API CALL
	 * 주식현재가 시세[v1_국내주식-008]
	 * @param request 조회할 주식 입력조건
	 * @return 주식데이터
	 */
	@Operation(summary = SwaggerDocs.SUMMARY_STOCK_INQUIRE_PRICE,
		description = SwaggerDocs.DESCRIPTION_STOCK_INQUIRE_PRICE)
	// @ApiErrorExceptions(BoardExceptionDocs.class)
	@PostMapping("/getInquirePrice")
	public ResponseEntity<Object> getInquirePrice(@RequestBody InquirePriceRequest request) {
		return stockService.getInquirePrice(request);
	}

}
