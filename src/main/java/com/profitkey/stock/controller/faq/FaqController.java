package com.profitkey.stock.controller.faq;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.dto.response.faq.FaqResponse;
import com.profitkey.stock.service.faq.FaqService;
import com.profitkey.stock.dto.request.faq.FaqCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.profitkey.stock.dto.response.faq.FaqListResponse;

@RestController
@RequestMapping("/api/faq")
@Slf4j
@RequiredArgsConstructor
public class FaqController {
	private final FaqService faqService;

	/**
	 * FAQ 생성 API
	 */
	@PostMapping(value = "")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CREATE, description = SwaggerDocs.DESCRIPTION_FAQ_CREATE)
	public ResponseEntity<FaqCreateResponse> createFaq(@RequestBody FaqCreateRequest request) {
		FaqCreateResponse result = faqService.createFaq(request.getPublished(), request.getTitle(),
			request.getQuestion(), request.getAnswer());
		return ResponseEntity.ok(result);
	}

	/**
	 * FAQ 목록 조회
	 */
	@GetMapping("/list")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_LIST, description = SwaggerDocs.DESCRIPTION_FAQ_LIST)
	public ResponseEntity<FaqListResponse> getFaqList() {
		FaqListResponse faqList = faqService.getFaqListByCategory();
		return ResponseEntity.ok(faqList);
	}

}
