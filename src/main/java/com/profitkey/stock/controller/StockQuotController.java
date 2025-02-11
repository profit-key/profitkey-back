package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.stock.InquireDailyRequest;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.dto.request.stock.InvestOpinionRequest;
import com.profitkey.stock.service.stock.StockQuotService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/quotations")
@RequiredArgsConstructor
@Slf4j
public class StockQuotController {
    private final StockQuotService stockDetailService;

    /**
     * [국내주식]기본시세 API CALL
     * 주식현재가 시세[v1_국내주식-008]
     *
     * @param request 조회할 주식 입력조건
     * @return 주식데이터
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_INQUIRE_PRICE,
            description = SwaggerDocs.DESCRIPTION_STOCK_INQUIRE_PRICE)
    @PostMapping("/inquire-price")
    public ResponseEntity<Object> getInquirePrice(@RequestBody InquirePriceRequest request) {
        return stockDetailService.getInquirePrice(request);
    }

    /**
     * [국내주식]기본시세 API CALL
     * 국내주식기간별시세(일/주/월/년)[v1_국내주식-016]
     *
     * @param request 조회할 주식 입력조건
     * @return 기간별 주식 데이터 다건
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_INQUIRE_DAILY,
            description = SwaggerDocs.DESCRIPTION_STOCK_INQUIRE_DAILY)
    @PostMapping("/inquire-daily")
    public ResponseEntity<Object> getInquireDaily(@RequestBody InquireDailyRequest request) {
        log.info("request {}", request);
        log.info("hello");
        return stockDetailService.getInquireDaily(request);
    }

    /**
     * [국내주식]업종/기타 API CALL
     * 국내주식 종목투자의견 [국내주식-188]
     *
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
