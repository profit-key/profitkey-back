package com.profitkey.stock.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.dto.response.faq.FaqResponse;
import com.profitkey.stock.service.FaqService;
import com.profitkey.stock.dto.request.PaginationRequest;
import com.profitkey.stock.dto.request.faq.FaqCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.profitkey.stock.dto.response.faq.FaqListResponse;

@RestController
@RequestMapping("/api/faq")
@Slf4j
@RequiredArgsConstructor
public class FaqController {
	private final FaqService faqService;

	/**
	 * FAQ 생성 API
	 * @return 생성한 FAQ 정보
	 */
	@PostMapping("")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CREATE, description = SwaggerDocs.DESCRIPTION_FAQ_CREATE)
	public ResponseEntity<FaqCreateResponse> createFaq(@RequestBody FaqCreateRequest request) {
		FaqCreateResponse result = faqService.createFaq(request.getPublished(), request.getTitle(),
			request.getQuestion(), request.getAnswer());
		return ResponseEntity.ok(result);
	}

	/**
	 * FAQ 목록 조회 API
	 * @return 조회한 FAQ 목록(페이징)
	 */
	@GetMapping("/list")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_LIST, description = SwaggerDocs.DESCRIPTION_FAQ_LIST)
	public ResponseEntity<FaqListResponse> getFaqList(
		@ParameterObject @ModelAttribute PaginationRequest request
	) {
		FaqListResponse response = faqService.getFaqListByCategory(request.getPage(), request.getSize());
		return ResponseEntity.ok(response);
	}

	/**
	 * FAQ 상세 조회
	 * @return 조회한 FAQ 상세 내역
	 */
	@GetMapping("/{id}")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_INFO, description = SwaggerDocs.DESCRIPTION_FAQ_INFO)
	public ResponseEntity<FaqResponse> getFaqById(
		@Parameter(description = "FAQ ID", required = true) 
		@PathVariable Long id
	) {
		FaqResponse response = faqService.getFaqById(id);
		return ResponseEntity.ok(response);
	}

}
