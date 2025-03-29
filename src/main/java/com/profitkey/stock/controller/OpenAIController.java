package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.AiAnalysisOpinion;
import com.profitkey.stock.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openai")
@RequiredArgsConstructor
public class OpenAIController {

	private final OpenAIService openAIService;

	@PostMapping("/chat")
	public String chat() {
		return openAIService.generateResponse();
	}

	@PostMapping("/opinion")
	@Operation(summary = SwaggerDocs.SUMMARY_AIOPINION_DAILY, description = SwaggerDocs.DESCRIPTION_AIOPINION_DAILY)
	public ResponseEntity<AiAnalysisOpinion> opinion() {
		return openAIService.getOpinion();
	}

} 