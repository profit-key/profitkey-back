package com.profitkey.stock.service.stock;

import com.profitkey.stock.config.ApiParser;
import com.profitkey.stock.dto.openapi.OpenApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DataGoService {
    private final OpenApiProperties openApiProperties;
    private final ApiParser apiParser;

    public String getStockInfo() {
        String enkey = openApiProperties.getApiKey();
        String baseUrl = openApiProperties.getStockPriceInfoUrl();
        String jsonStringfy = apiParser.fetchItemsAsJson(baseUrl, enkey);
        return jsonStringfy;
    }
}
