package com.profitkey.stock.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.openai.ChatGPTRequest;
import com.profitkey.stock.dto.openai.ChatGPTResponse;
import com.profitkey.stock.dto.openai.Message;
import com.profitkey.stock.dto.request.stock.FluctuationDefaultRequest;
import com.profitkey.stock.dto.request.stock.FluctuationRequest;
import com.profitkey.stock.dto.request.stock.GrowthRatioDefaultRequest;
import com.profitkey.stock.dto.request.stock.GrowthRatioRequest;
import com.profitkey.stock.dto.request.stock.ProfitRatioDefaultRequest;
import com.profitkey.stock.dto.request.stock.ProfitRatioRequest;
import com.profitkey.stock.dto.request.stock.StabilityRatioDefaultRequest;
import com.profitkey.stock.dto.request.stock.StabilityRatioRequest;
import com.profitkey.stock.entity.AiAnalysisOpinion;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.repository.OpenAIRepositiory;
import com.profitkey.stock.repository.stock.StockCodeRepository;
import com.profitkey.stock.service.stock.StockItemService;
import com.profitkey.stock.service.stock.StockRankService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService {
	private final RestTemplate openaiRestTemplate;
	private final int MAX_RETRIES = 3;
	private final long RETRY_DELAY_MS = 1000;
	private final OpenAIRepositiory openAIRepositiory;
	private final StockCodeRepository stockCodeRepository;
	private final StockRankService stockRankService;
	private final StockItemService stockItemService;
	private final ObjectMapper objectMapper;

	@Value("${openai.api.url}")
	private String apiUrl;

	@Value("${openai.model}")
	private String model;

	public String generateResponse() {
		int retries = 0;
		// 기본 요청 값 설정
		FluctuationRequest fluctuationRequest = new FluctuationDefaultRequest();

		// 등락률 정보 조회 및 문자열로 변환
		String fluctuationData = stockRankService.getFluctuation(fluctuationRequest).toString();
		log.info("generateResponse fluctuationData : {}", fluctuationData);
		while (retries < MAX_RETRIES) {
			try {
				String a = callOpenAIApi("\n\n주식 데이터: " + fluctuationData
					+ "이 데이터는 등락율기준 30개의 등락률이 큰 종목을 고른건데 이중에서 유심히 볼 3개 종목의 항목코드를 [\"...\"] 형태의 문자열 리스트만 반환해줘. Java 코드 없이, 배열 요소만 문자열로.");
				return stockConver(a);
			} catch (RestClientException | InterruptedException e) {
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
			}
		}
		throw new RuntimeException("API 호출 재시도 횟수를 초과했습니다.");
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

	public String stockConver(String str) throws InterruptedException {
		String[] stockCodes = str
			.split("\\s*,\\s*|\\s*\\n\\s*"); // 쉼표 또는 줄바꿈 기준으로 나눔

		// 불필요한 따옴표 제거
		for (int i = 0; i < stockCodes.length; i++) {
			stockCodes[i] = stockCodes[i].replaceAll("\"", "").trim();
		}
		return vertifAi(stockCodes);
	}

	public String vertifAi(String[] stockCodes) throws InterruptedException {
		String response = "";
		for (int i = 0; i < stockCodes.length; i++) {
			response += "\n종목코드 : " + stockCodes[i] + "\n";
			Thread.sleep(RETRY_DELAY_MS);
			ProfitRatioRequest profitRatioRequest = new ProfitRatioDefaultRequest(stockCodes[i]);
			ResponseEntity<Object> responseProfitRatio =
				stockItemService.getProfitRatio(profitRatioRequest); // 수익성
			Map<String, Object> mapProfitRatio =
				objectMapper.convertValue(responseProfitRatio.getBody(), new TypeReference<Map<String, Object>>() {
				});
			List<Map<String, Object>> output1List = (List<Map<String, Object>>)mapProfitRatio.get("output");
			response += "수익성 : \n";
			if (output1List != null && !output1List.isEmpty()) {
				response += output1List.get(0).toString();
			}
			Thread.sleep(RETRY_DELAY_MS);
			StabilityRatioRequest stabilityRatioRequest = new StabilityRatioDefaultRequest(stockCodes[i]);
			ResponseEntity<Object> responseStabilityRatio =
				stockItemService.getStabilityRatio(stabilityRatioRequest); // 안정성
			Map<String, Object> mapStabilityRatio =
				objectMapper.convertValue(responseStabilityRatio.getBody(), new TypeReference<Map<String, Object>>() {
				});
			List<Map<String, Object>> output2List = (List<Map<String, Object>>)mapStabilityRatio.get("output");
			response += "안정성 : \n";
			if (output2List != null && !output2List.isEmpty()) {
				response += output2List.get(0);
			}
			Thread.sleep(RETRY_DELAY_MS);
			GrowthRatioRequest growthRatioRequest = new GrowthRatioDefaultRequest(stockCodes[i]);
			ResponseEntity<Object> responseGrowthRatio =
				stockItemService.getGrowthRatio(growthRatioRequest); // 성장성
			Map<String, Object> mapSGrowthRatio =
				objectMapper.convertValue(responseGrowthRatio.getBody(), new TypeReference<Map<String, Object>>() {
				});
			List<Map<String, Object>> output3List = (List<Map<String, Object>>)mapSGrowthRatio.get("output");
			response += "성장성 : \n";
			if (output3List != null && !output3List.isEmpty()) {
				response += output3List.get(0);
			}
		}
		String prom = callOpenAIApi(response + "불필요한 말 제외하고 위 데이터를 기준으로 종목 하나를 선택해서 아래 항목 작성해줘"
			+ "추천종목 : \n"
			+ "추천사유 : \n");

		Pattern pattern = Pattern.compile("(\\d{6})");
		Matcher matcher = pattern.matcher(prom);

		String code = "000000";
		if (matcher.find()) {
			code = matcher.group(1); // 6자리 종목코드
		}
		log.info("stock code : {}", code);
		StockCode stockCode = stockCodeRepository.findByStockCode(code);
		prom = prom.replaceAll(code, stockCode.getStockName());

		AiAnalysisOpinion aiAnalysisOpinion = AiAnalysisOpinion.builder()
			.aiRequest("Daily")
			.aiResponse(prom)
			.stockCode(stockCode)
			.build();
		openAIRepositiory.save(aiAnalysisOpinion);
		return prom;
	}

	public ResponseEntity<AiAnalysisOpinion> getOpinion() {
		Optional<AiAnalysisOpinion> aiAnalysisOpinion = openAIRepositiory.findTopByOrderByCreatedAtDesc();
		return ResponseEntity.ok(aiAnalysisOpinion.get());
	}
} 