package com.profitkey.stock.controller;

import com.profitkey.stock.service.StockService;
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

    @GetMapping
    public String getStockData() {
        try {
            return stockService.getStockInfo();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
