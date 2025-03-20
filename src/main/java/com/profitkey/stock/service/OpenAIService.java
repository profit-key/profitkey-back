package com.profitkey.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.openai.ChatGPTRequest;
import com.profitkey.stock.dto.openai.ChatGPTResponse;
import com.profitkey.stock.dto.openai.Message;
import com.profitkey.stock.dto.request.stock.FluctuationRequest;
import com.profitkey.stock.dto.request.stock.GrowthRatioRequest;
import com.profitkey.stock.dto.request.stock.ProfitRatioRequest;
import com.profitkey.stock.dto.request.stock.StabilityRatioRequest;
import com.profitkey.stock.service.stock.StockItemService;
import com.profitkey.stock.service.stock.StockRankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService {

	private final KisApiProperties kisApiProperties;
	private final RestTemplate openaiRestTemplate;
	private final int MAX_RETRIES = 3;
	private final long RETRY_DELAY_MS = 1000;
	@Autowired
	private final StockRankService stockRankService;
	private final StockItemService stockItemService;

	@Value("${openai.api.url}")
	private String apiUrl;

	@Value("${openai.model}")
	private String model;

	public String generateResponse() {
		int retries = 0;

		// 기본 요청 값 설정
		FluctuationRequest fluctuationRequest = new FluctuationRequest(
			"FHPST01700000",  // tr_id
			"P",              // custtype
			"string",         // fidRsflRate2
			"J",              // fidCondMrktDivCode
			"20170",          // fidCondScrDivCode
			"0000",           // fidInputIscd
			"0",              // fidRankSortClsCode
			"0",              // fidInputCnt1
			"0",              // fidPrcClsCode
			"string",         // fidInputPrice1
			"string",         // fidInputPrice2
			"string",         // fidVolCnt
			"0",              // fidTrgtClsCode
			"0",              // fidTrgtExlsClsCode
			"0",              // fidDivClsCode
			"string"          // fidRsflRate1
		);

		// 등락률 정보 조회 및 문자열로 변환
		String fluctuationData = stockRankService.getFluctuation(fluctuationRequest).toString();
		System.out.println(fluctuationData);

		ProfitRatioRequest profitRatioRequest = new ProfitRatioRequest(
			"FHPST01700000",  // tr_id
			"P",               // fidInputIscd
			"string",          // fidDivClsCode
			"J"                // fidCondMrktDivCode
		);

		// 수익성 정보 조회 (getProfitRatio 메서드를 호출)
		String profitRatioData = stockItemService.getProfitRatio(profitRatioRequest).toString();
		System.out.println("Profit Ratio Data: " + profitRatioData);

		// StabilityRatio 요청 객체 생성
		StabilityRatioRequest stabilityRatioRequest = new StabilityRatioRequest(
			"FHPST01700000",  // tr_id
			"P",               // custtype
			"string",          // fidInputIscd
			"string",          // fidDivClsCode
			"J"                // fidCondMrktDivCode
		);

		// 안정성 정보 조회 (getStabilityRatio 메서드를 호출)
		String stabilityRatioData = stockItemService.getStabilityRatio(stabilityRatioRequest).toString();
		System.out.println("Stability Ratio Data: " + stabilityRatioData);

		// GrowthRatio 요청 객체 생성
		GrowthRatioRequest growthRatioRequest = new GrowthRatioRequest(
			"FHPST01700000",  // tr_id
			"P",               // custtype
			"string",          // fidInputIscd
			"string",          // fidDivClsCode
			"J"                // fidCondMrktDivCode
		);

		// 성장성 정보 조회 (getGrowthRatio 메서드를 호출)
		String growthRatioData = stockItemService.getGrowthRatio(growthRatioRequest).toString();
		System.out.println("Growth Ratio Data: " + growthRatioData);

		while (retries < MAX_RETRIES) {
			try {
				Thread.sleep(10000);

				String a = callOpenAIApi("\n\n등락률 데이터: " + fluctuationData
					+ "이 데이터는 등락률 기준 30개의 등락률이 큰 종목을 고른 건데, 이 중에서 유심히 볼 5개 종목을 선택해 주세    요. "
					+ "\n안정성 데이터: " + stabilityRatioData
					+ "\n성장성 데이터: " + growthRatioData
					+ "\n수익성 데이터: " + profitRatioData
					+ "선택 기준은 '수익성', '안정성', '성장성'을 고려하여 종목을 선택해야 합니다. "
					+ "선택된 5개 종목의 항목코드를 리스트 형태로 반환해 주세요. 자바에서 사용할 것이기 때문에 항목코드만 [] 안에 문자열로 넣어주면 됩니다.");
				return parseStockCodes(a);
			} catch (RestClientException e) {
				if (e.getMessage().contains("insufficient_quota")) {
					log.error("API quota exceeded", e);
					throw new RuntimeException("API 할당량이 초과되었습니다. 관리자에게 문의하세요.");
				}
				retries++;
				if (retries < MAX_RETRIES) {
					try {
						Thread.sleep(RETRY_DELAY_MS);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("API 호출 재시도 횟수를 초과했습니다.");
	}

	private String parseStockCodes(String response) {
		// 응답에서 종목 코드들만 추출
		// 예시 응답 형식: String selectedStocks = {"199730", "009410", "223310", "317530", "011300"};

		// 정규식을 사용하여 숫자 6자리 종목 코드 추출
		Pattern pattern = Pattern.compile("\"(\\d{6})\"");
		Matcher matcher = pattern.matcher(response);

		// 매칭된 종목 코드들을 리스트에 저장
		List<String> stockCodesList = new ArrayList<>();
		while (matcher.find()) {
			stockCodesList.add(matcher.group(1));
		}

		// 종목 코드 리스트를 원하는 형식으로 반환
		String stockCodes = String.join(", ", stockCodesList);

		// 최종 응답 형식에 맞게 문구 추가
		return "등락률 상위 30개 기준으로 안정성/수익성/성장성을 고려하여 아래 다섯가지 종목을 추천하고 싶습니다!\n" + stockCodes;
	}

	private String callOpenAIApi(String prompt) {
		try {
			ChatGPTRequest request = new ChatGPTRequest(model, prompt);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

			ChatGPTResponse response = openaiRestTemplate.postForObject(apiUrl, entity, ChatGPTResponse.class);

			// Null 체크 및 응답 검증
			if (response == null) {
				log.error("OpenAI API returned null response");
				throw new RuntimeException("OpenAI API 응답이 없습니다.");
			}

			if (response.getChoices() == null || response.getChoices().isEmpty()) {
				log.error("OpenAI API returned empty choices");
				throw new RuntimeException("OpenAI API 응답에 선택지가 없습니다.");
			}

			Message message = response.getChoices().get(0).getMessage();
			if (message == null || message.getContent() == null) {
				log.error("OpenAI API returned null message content");
				throw new RuntimeException("OpenAI API 응답 내용이 없습니다.");
			}

			return message.getContent();

		} catch (RestClientException e) {
			log.error("Failed to call OpenAI API", e);
			throw new RuntimeException("OpenAI API 호출 중 오류가 발생했습니다: " + e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error while processing OpenAI response", e);
			throw new RuntimeException("OpenAI 응답 처리 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
} 