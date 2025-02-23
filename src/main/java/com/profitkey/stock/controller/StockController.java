package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.handler.BatchScheduler;
import com.profitkey.stock.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

	private final StockService stockService;
	private final BatchScheduler batchScheduler;

	/**
	 * OAuth인증
	 * 접근토큰발급(P)[인증-001]
	 * * 1분에 하나씩 발급가능
	 * @param
	 * @return access_token
	 */
	@Operation(summary = SwaggerDocs.SUMMARY_STOCK_TOKEN,
		description = SwaggerDocs.DESCRIPTION_STOCK_TOKEN)
	@GetMapping("/get-token")
	public String getToken() throws IOException {
		return stockService.getToken();
	}

	/**
	 * 종목명조회 API
	 * @param code 종목코드
	 * @return 종목명
	 */
	@GetMapping("/{code}")
	public String getStockName(@PathVariable String code) {
		return stockService.getStockNameByCode(code);
	}

	/**
	 * 종목명조회 API
	 * @param code 종목코드 앞자리
	 * @return 종목목록
	 */
	@GetMapping("/search/{code}")
	public List<StockCode> searchStocks(@PathVariable String code) {
		return stockService.searchStocksByCodePattern(code);
	}

	// 배치 테스트용
	@GetMapping("/createStockInfo")
	public void createStockInfoJob()
		throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException,
		JobRestartException {
		batchScheduler.runJob();
	}
}
