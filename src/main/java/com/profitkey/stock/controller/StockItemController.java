package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.stock.*;
import com.profitkey.stock.service.stock.StockItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/item")
@RequiredArgsConstructor
@Slf4j
public class StockItemController {

    private final StockItemService stockItemService;

    /**
     * [국내주식]종목정보 API CALL
     * 국내주식 손익계산서[v1_국내주식-079]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_INCOME_STATMENT,
            description = SwaggerDocs.DESCRIPTION_STOCK_INCOME_STATMENT)
    @PostMapping("/income-statement")
    public ResponseEntity<Object> getIncomeStatement(@RequestBody IncomeStatementRequest request) {
        return stockItemService.getIncomeStatement(request);
    }

    /**
     * [국내주식]종목정보 API CALL
     * 국내주식 재무비율[v1_국내주식-080]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_FINANCIAL_RATIO,
            description = SwaggerDocs.DESCRIPTION_STOCK_FINANCIAL_RATIO)
    @PostMapping("/financial-ratio")
    public ResponseEntity<Object> getFinancialRatio(@RequestBody FinancialRatioRequest request) {
        return stockItemService.getFinancialRatio(request);
    }

    /**
     * [국내주식]종목정보 API CALL
     * 국내주식 수익성비율[v1_국내주식-081]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_PROFIT_RATIO,
            description = SwaggerDocs.DESCRIPTION_STOCK_PROFIT_RATIO)
    @PostMapping("/profit-ratio")
    public ResponseEntity<Object> getProfitRatio(@RequestBody ProfitRatioRequest request) {
        return stockItemService.getProfitRatio(request);
    }

    /**
     * [국내주식]종목정보 API CALL
     * 국내주식 안정성비율[v1_국내주식-083]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_STABILITY_RATIO,
            description = SwaggerDocs.DESCRIPTION_STOCK_STABILITY_RATIO)
    @PostMapping("/stability-ratio")
    public ResponseEntity<Object> getStabilityRatio(@RequestBody StabilityRatioRequest request) {
        return stockItemService.getStabilityRatio(request);
    }

    /**
     * [국내주식]종목정보 API CALL
     * 국내주식 성장성비율[v1_국내주식-085]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_GROWTH_RATIO,
            description = SwaggerDocs.DESCRIPTION_STOCK_GROWTH_RATIO)
    @PostMapping("/growth-ratio")
    public ResponseEntity<Object> getGrowthRatio(@RequestBody GrowthRatioRequest request) {
        return stockItemService.getGrowthRatio(request);
    }

}
