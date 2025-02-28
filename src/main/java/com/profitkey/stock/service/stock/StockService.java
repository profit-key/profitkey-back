package com.profitkey.stock.service.stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.request.stock.DividendDefaultRequest;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.dto.request.stock.MarketCapDefaultRequest;
import com.profitkey.stock.dto.request.stock.MarketCapRequest;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.entity.StockInfo;
import com.profitkey.stock.entity.StockSort;
import com.profitkey.stock.repository.stock.StockCodeRepository;
import com.profitkey.stock.repository.stock.StockRepository;
import com.profitkey.stock.util.DataConversionUtil;
import com.profitkey.stock.util.DateTimeUtil;
import com.profitkey.stock.util.HttpClientUtil;
import java.io.IOException;
import java.util.HashMap;
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
	private final StockItemService stockItemService;
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
		stockDataList.forEach(stockData -> saveStockInfo(stockData, StockSort.MARKET_CAP));
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
				ResponseEntity<Object> responseIp =
					stockQuotService.getInquirePrice(new InquirePriceRequest("FHKST01010100", "J", code));
				// 배당일정 호출
				ResponseEntity<Object> responseDr =
					stockItemService.getDividend(new DividendDefaultRequest(code));

				Map<String, Object> inquirePriceMap =
					objectMapper.convertValue(responseIp.getBody(), new TypeReference<Map<String, Object>>() {
					});
				Map<String, Object> dividendMap =
					objectMapper.convertValue(responseDr.getBody(), new TypeReference<Map<String, Object>>() {
					});
				Map<String, Object> output = (Map<String, Object>)inquirePriceMap.get("output");
				List<Map<String, Object>> output2 = (List<Map<String, Object>>)dividendMap.get("output1");

				Map<String, Object> filteredOutput = new HashMap<>();
				if (output != null) {
					filteredOutput.put("stock_code", code);
					filteredOutput.put("w52_hgpr", output.get("w52_hgpr"));
					filteredOutput.put("w52_lwpr", output.get("w52_lwpr"));
					filteredOutput.put("per", output.get("per"));
					filteredOutput.put("pbr", output.get("pbr"));
					filteredOutput.put("eps", output.get("eps"));
					filteredOutput.put("bps", output.get("bps"));
					filteredOutput.put("dryy_hgpr_vrss_prpr_rate", output.get("dryy_hgpr_vrss_prpr_rate"));
					filteredOutput.put("dryy_lwpr_vrss_prpr_rate", output.get("dryy_lwpr_vrss_prpr_rate"));
					if (output2 != null) {
						Map<String, Object> firstRecord = output2.get(0);
						filteredOutput.put("divi_rate", firstRecord.get("divi_rate"));
						filteredOutput.put("divi_amt", firstRecord.get("eper_sto_divi_amt"));
					}
				}
				log.info("filteredOutput : {}", filteredOutput);
				return filteredOutput;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}

	private void saveStockInfo(Map<String, Object> stockData, StockSort stockSort) {
		stockRepository.save(buildStockInfo(stockData, stockSort));
	}

	private StockInfo buildStockInfo(Map<String, Object> output, StockSort stockSort) {
		String stockCodeStr = (String)output.get("stock_code");
		StockCode stockCode = stockCodeRepository.findByStockCode(stockCodeStr);

		return StockInfo.builder()
			.stockCode(stockCode)
			.baseDate(DateTimeUtil.curDate(""))    // 날짜
			.division(stockSort)    // 구분
			.endingPrice(DataConversionUtil.toBigDecimal(output.get("stck_prpr")))
			.openingPrice(DataConversionUtil.toInteger(output.get("stck_oprc")))    // 시가
			.highPrice(DataConversionUtil.toInteger(output.get("stck_hgpr")))    // 주식최고가
			.lowPrice(DataConversionUtil.toInteger(output.get("stck_lwpr")))    // 주식최저가
			.tradingVolume(DataConversionUtil.toLong(output.get("acml_vol")))    // 거래량
			.tradingValue(DataConversionUtil.toLong(output.get("acml_tr_pbmn")))    // 거래대금
			.marketCap(DataConversionUtil.toLong(output.get("hts_avls")))    // 시가총액
			.fiftyTwoWeekHigh(DataConversionUtil.toInteger(output.get("w52_hgpr")))    // 52주 최고가
			.fiftyTwoWeekLow(DataConversionUtil.toInteger(output.get("w52_lwpr")))    // 52주 최저가
			.per(DataConversionUtil.toBigDecimal(output.get("per")))
			.eps(DataConversionUtil.toBigDecimal(output.get("eps")))
			.pbr(DataConversionUtil.toBigDecimal(output.get("pbr")))
			.bps(DataConversionUtil.toBigDecimal(output.get("bps")))
			.diviRate(DataConversionUtil.toBigDecimal(output.get("divi_rate")))    // 배당률
			.diviAmt(DataConversionUtil.toBigDecimal(output.get("divi_amt")))    // 배당금
			.build();
	}

}
