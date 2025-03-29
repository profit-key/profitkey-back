package com.profitkey.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.AiAnalysisOpinion;
import com.profitkey.stock.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<AiAnalysisOpinion> opinionDaily() {
		return openAIService.getOpinion();
	}

	@PostMapping("/opinion/{code}")
	@Operation(summary = SwaggerDocs.SUMMARY_AIOPINION_DAILY, description = SwaggerDocs.DESCRIPTION_AIOPINION_DAILY)
	public ResponseEntity<AiAnalysisOpinion> opinionStock(@PathVariable String code)
		throws InterruptedException, JsonProcessingException {
		return openAIService.getOpinionStock(code);
	}

}