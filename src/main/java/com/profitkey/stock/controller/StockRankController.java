package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.stock.FluctuationRequest;
import com.profitkey.stock.dto.request.stock.MarketCapRequest;
import com.profitkey.stock.dto.request.stock.VolumeRankRequest;
import com.profitkey.stock.service.stock.StockRankService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/rank")
@RequiredArgsConstructor
@Slf4j
public class StockRankController {
    private final StockRankService stockRankService;

    /**
     * [국내주식]순위분석 API CALL
     * 거래량순위[v1_국내주식-047]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_VOLUME_RANK,
            description = SwaggerDocs.DESCRIPTION_STOCK_VOLUME_RANK)
    @PostMapping("/volume-rank")
    public ResponseEntity<Object> getVolumeRank(@RequestBody VolumeRankRequest request) {
        return stockRankService.getVolumeRank(request);
    }

    /**
     * [국내주식]순위분석 API CALL
     * 국내주식 등락률 순위[v1_국내주식-088]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_FLUCTUATION,
            description = SwaggerDocs.DESCRIPTION_STOCK_FLUCTUATION)
    @PostMapping("/fluctuation")
    public ResponseEntity<Object> getFluctuation(@RequestBody FluctuationRequest request) {
        return stockRankService.getFluctuation(request);
    }

    /**
     * [국내주식]순위분석 API CALL
     * 국내주식 시가총액 상위[v1_국내주식-091]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_MARKET_CAP,
            description = SwaggerDocs.DESCRIPTION_STOCK_MARKET_CAP)
    @PostMapping("/market-cap")
    public ResponseEntity<Object> getMarketCap(@RequestBody MarketCapRequest request) {
        return stockRankService.getMarketCap(request);
    }

}
