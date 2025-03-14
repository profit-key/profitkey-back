package com.profitkey.stock.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.openai.ChatGPTRequest;
import com.profitkey.stock.dto.openai.ChatGPTResponse;
import com.profitkey.stock.dto.openai.Message;
import com.profitkey.stock.dto.request.stock.FluctuationRequest;
import com.profitkey.stock.service.stock.StockRankService;
import com.profitkey.stock.util.HeaderUtil;
import com.profitkey.stock.util.HttpClientUtil;

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
		while (retries < MAX_RETRIES) {
			try {
				String a = callOpenAIApi("\n\n주식 데이터: " + fluctuationData + "이 데이터는 등락율기준 30개의 등락률이 큰 종목을 고른건데 이중에서 유심히 볼 5개정도의 항목을 항목코드만 리스트 형태로 반환해줘 자바에서 사용할것이기 때문에 []안에 항목만 문자열로 넣어주면 될것 같아");
				return a;
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
} 