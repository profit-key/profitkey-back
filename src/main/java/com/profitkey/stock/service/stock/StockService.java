package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.dto.request.stock.MarketCapDefaultRequest;
import com.profitkey.stock.dto.request.stock.MarketCapRequest;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.entity.StockInfo;
import com.profitkey.stock.repository.stock.StockCodeRepository;
import com.profitkey.stock.repository.stock.StockRepository;
import com.profitkey.stock.util.DataConversionUtil;
import com.profitkey.stock.util.DateTimeUtil;
import com.profitkey.stock.util.HttpClientUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
	private final KisApiProperties kisApiProperties;
	private final StockRankService stockRankService;
	private final StockQuotService stockQuotService;
	private final StockRepository stockRepository;
	private final StockCodeRepository stockCodeRepository;
	private final ObjectMapper objectMapper;

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

	public String getStockNameByCode(String code) {
		StockCode stockCode = stockCodeRepository.findByStockCode(code);
		return (stockCode != null) ? stockCode.getStockName() : "";
	}

	public List<StockCode> searchStocksByCodePattern(String code) {
		return stockCodeRepository.findByStockCodeLike(code + "%");
	}

	public void createStockInfo() {
		log.info("start job createStockInfo");

		List<String> stockCodes = getTopStockCodes();
		List<Map<String, Object>> stockDataList = fetchStockData(stockCodes);

		stockDataList.forEach(this::saveStockInfo);
	}

	private List<String> getTopStockCodes() {
		// 주식 시가총액 상위 get
		MarketCapRequest marketCapRequest = new MarketCapDefaultRequest();
		Map<String, Object> marketCapMap = objectMapper.convertValue(
			stockRankService.getMarketCap(marketCapRequest).getBody(),
			new TypeReference<>() {
			}
		);
		List<Map<String, Object>> outputList = (List<Map<String, Object>>)marketCapMap.get("output");

		return outputList.stream()
			.map(stock -> (String)stock.get("mksc_shrn_iscd"))
			.limit(3) // 테스트용으로 상위 3개만 제한
			.collect(Collectors.toList());
	}

	private List<Map<String, Object>> fetchStockData(List<String> stockCodes) {
		return stockCodes.stream().map(code -> {
			try {
				Thread.sleep(1000);
				// 주식기본시세 호출
				ResponseEntity<Object> response =
					stockQuotService.getInquirePrice(new InquirePriceRequest("FHKST01010100", "J", code));

				return objectMapper.readValue(
					objectMapper.writeValueAsString(response.getBody()),
					new TypeReference<Map<String, Object>>() {
					}
				);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("JSON 변환 오류", e);
			}
		}).collect(Collectors.toList());
	}

	private void saveStockInfo(Map<String, Object> stockData) {
		log.info("Stock Data: {}", stockData);
		Map<String, Object> output = (Map<String, Object>)stockData.get("output");
		stockRepository.save(buildStockInfo(output));
	}

	private StockInfo buildStockInfo(Map<String, Object> output) {
		String stockCodeStr = (String)output.get("stck_shrn_iscd");
		StockCode stockCode = stockCodeRepository.findByStockCode(stockCodeStr);

		return StockInfo.builder()
			.stockCode(stockCode)
			.baseDate(DateTimeUtil.curDate(""))
			.endingPrice(DataConversionUtil.toBigDecimal(output.get("stck_prpr")))
			.openingPrice(DataConversionUtil.toInteger(output.get("opening_price")))
			.highPrice(DataConversionUtil.toInteger(output.get("high_price")))
			.lowPrice(DataConversionUtil.toInteger(output.get("low_price")))
			.tradingVolume(DataConversionUtil.toLong(output.get("trading_volume")))
			.tradingValue(DataConversionUtil.toLong(output.get("trading_value")))
			.marketCap(DataConversionUtil.toLong(output.get("market_cap")))
			.fiftyTwoWeekHigh(DataConversionUtil.toInteger(output.get("fifty_two_week_high")))
			.fiftyTwoWeekLow(DataConversionUtil.toInteger(output.get("fifty_two_week_low")))
			.per(DataConversionUtil.toBigDecimal(output.get("per")))
			.eps(DataConversionUtil.toBigDecimal(output.get("eps")))
			.pbr(DataConversionUtil.toBigDecimal(output.get("pbr")))
			.bps(DataConversionUtil.toBigDecimal(output.get("bps")))
			.build();
	}

}
