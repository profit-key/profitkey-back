package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;

    /**
     * OAuth인증
     * 접근토큰발급(P)[인증-001]
     * * 1분에 하나씩 발급가능
     *
     * @param
     * @return access_token
     */
    @Operation(summary = SwaggerDocs.SUMMARY_STOCK_TOKEN,
            description = SwaggerDocs.DESCRIPTION_STOCK_TOKEN)
    @GetMapping("/get-token")
    public String getToken() throws IOException {
        return stockService.getToken();
    }


}
