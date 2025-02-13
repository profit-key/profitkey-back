package com.profitkey.stock.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.annotation.ApiErrorExceptions;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.PaginationRequest;
import com.profitkey.stock.dto.request.faq.FaqCreateRequest;
import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.dto.response.faq.FaqListResponse;
import com.profitkey.stock.exception.docs.faqcategory.CreateFaqExceptionDocs;
import com.profitkey.stock.service.FaqService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	@ApiErrorExceptions(CreateFaqExceptionDocs.class)
	public ResponseEntity<FaqCreateResponse> createFaq(@RequestBody FaqCreateRequest request) {
		FaqCreateResponse result = faqService.createFaq(request.getPublished(), request.getQuestion(),
			request.getAnswer());
		return ResponseEntity.ok(result);
	}

	/**
	 * FAQ 목록 조회 API
	 * @return 조회한 FAQ 목록(페이징)
	 */
	@GetMapping("/list")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_LIST, description = SwaggerDocs.DESCRIPTION_FAQ_LIST)
	public ResponseEntity<FaqListResponse> getFaqList(@ParameterObject @ModelAttribute PaginationRequest request) {
		FaqListResponse response = faqService.getFaqListByCategory(request.getPage(), request.getSize());
		return ResponseEntity.ok(response);
	}
}
