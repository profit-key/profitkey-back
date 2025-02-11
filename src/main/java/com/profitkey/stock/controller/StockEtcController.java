package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.stock.InvestOpinionRequest;
import com.profitkey.stock.service.stock.StockEtcService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/etc")
@RequiredArgsConstructor
@Slf4j
public class StockEtcController {
	private final StockEtcService stockDetailService;

	/**
	 * [국내주식]업종/기타 API CALL
	 * 국내주식 종목투자의견 [국내주식-188]
	 * @param request 조회할 주식 입력조건
	 * @return
	 */
	@Operation(summary = SwaggerDocs.SUMMARY_STOCK_INVEST_OPINION,
		description = SwaggerDocs.DESCRIPTION_STOCK_INVEST_OPINION)
	@PostMapping("/invest-opinion")
	public ResponseEntity<Object> getInvestOpinion(@RequestBody InvestOpinionRequest request) {
		return stockDetailService.getInvestOpinion(request);
	}

}
