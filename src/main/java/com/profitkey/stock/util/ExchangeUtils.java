package com.profitkey.stock.util;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.dto.ExchangeDto;

@Component
public class ExchangeUtils {
	@Value("${property.exchange.authKey}")
	private String authkey;

	@Value("${property.exchange.data}")
	private String data;

	private final String searchDate = getSearchDate();

	WebClient webClient;

	/*
		Open API 개발명세의 요청 URL (Request URL) + 요청변수 형식을 구성하여 Get 방식을 사용하였습니다.
		WebClient를 사용하여 외부 API를 호출할 땐 인코딩을 주의해야 합니다.
		DefaultUriBuilderFactory 객체를 생성하여 인코딩 모드를 None으로 변경하고 이를 아래와 같이 WebClient에 적용했습니다.
		queryParam을 사용할 때, API를 WebClient로 호출하기 위해서 인코딩을 하지 않도록 처리하였습니다.
	*/
	public JsonNode getExchangeDataSync() {

		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

		// WebClient를 생성합니다.
		webClient = WebClient.builder().uriBuilderFactory(factory).build();

		// WebClient를 사용하여 동기적으로 데이터를 요청하고, 바로 parseJson 함수를 호출합니다.
		String responseBody = webClient.get()
			.uri(builder -> builder
				.scheme("https")
				.host("www.koreaexim.go.kr")
				.path("/site/program/financial/exchangeJSON")
				.queryParam("authkey", authkey)
				.queryParam("searchdate", searchDate)
				.queryParam("data", data)
				.build())
			.retrieve()
			.bodyToMono(String.class)
			.block(); // 동기적으로 결과를 얻음
		return parseJson(responseBody);
	}

	/*
		getExchangeDataSync()에서 가져온 결과 값 (String responseBody)을 Json 형식으로 나타내기 위한 작업입니다.
	*/
	private JsonNode parseJson(String responseBody) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(responseBody);
		} catch (IOException e) {
			// 예외 처리 필요
			e.printStackTrace();
			return null;
		}
	}

	/*
		주말(토요일, 일요일) 에는 환율 정보가 들어오지 않습니다.
		파라미터에 값을 설정하기 위하여 토요일, 일요일 모두 금요일로 설정하도록 하는 함수입니다.
		특정 조건들을 추가하여 특정 일자를 호출하고 싶다면 이 메서드에서 수정하면 됩니다.
	*/
	private String getSearchDate() {

		LocalDate currentDate = LocalDate.now();
		DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
		// 토요일
		if (dayOfWeek.getValue() == 6)
			return currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		// 일요일
		if (dayOfWeek.getValue() == 7)
			return currentDate.minusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		return currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public List<ExchangeDto> getExchangeDataAsDtoList() {
		JsonNode jsonNode = getExchangeDataSync();

		if (jsonNode != null && jsonNode.isArray()) {
			List<ExchangeDto> exchangeDtoList = new ArrayList<>();

			for (JsonNode node : jsonNode) {
				ExchangeDto exchangeDto = convertJsonToExchangeDto(node);
				exchangeDtoList.add(exchangeDto);
			}

			return exchangeDtoList;
		}

		return Collections.emptyList();
	}

	private ExchangeDto convertJsonToExchangeDto(JsonNode jsonNode) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.treeToValue(jsonNode, ExchangeDto.class);
		} catch (JsonProcessingException e) {
			// 예외 처리 필요
			e.printStackTrace();
			return null;
		}
	}
}