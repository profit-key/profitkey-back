package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.util.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final KisApiProperties kisApiProperties;

    @Cacheable(value = "tokenCache", key = "'stockToken'", unless = "#result == null")
    public String getToken() throws IOException {
        String url = kisApiProperties.getOauth2TokenUrl();
        String data = """
                {
                    "grant_type": "client_credentials",
                    "appkey": "%s",
                    "appsecret": "%s"
                }
                """.formatted(kisApiProperties.getApiKey(), kisApiProperties.getSecretKey());
        ;

        String jsonResponse = HttpClientUtil.sendPostRequest(url, data);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        return rootNode.get("access_token").asText();
    }


}
