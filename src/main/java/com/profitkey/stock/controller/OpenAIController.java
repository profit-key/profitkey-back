package com.profitkey.stock.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.service.OpenAIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/openai")
@RequiredArgsConstructor
public class OpenAIController {

	private final OpenAIService openAIService;

	@PostMapping("/chat")
	public List<String> chat() {
		// 종목 코드를 리스트 형태로 반환
		String response = openAIService.generateResponse();

		// 콤마로 구분된 종목 코드들을 리스트로 변환
		return Arrays.asList(response.split(","));
	}
}
