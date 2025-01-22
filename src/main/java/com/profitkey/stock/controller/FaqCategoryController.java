package com.profitkey.stock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.annotation.ApiErrorExceptions;
import com.profitkey.stock.exception.docs.faqcategory.FaqCategoryExceptionDocs;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.faq.FaqCategoryRequest;
import com.profitkey.stock.dto.response.faq.FaqCategoryResponse;
import com.profitkey.stock.service.faq.FaqCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/faq-category")
@Slf4j
@RequiredArgsConstructor
public class FaqCategoryController {
	private final FaqCategoryService faqCategoryService;

	@PostMapping("/create")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CATEGORY_CREATE,
		description = SwaggerDocs.DESCRIPTION_FAQ_CATEGORY_CREATE)
	@ApiErrorExceptions(FaqCategoryExceptionDocs.class)
	public ResponseEntity<FaqCategoryResponse> createFaqCategory(
		@RequestBody FaqCategoryRequest request
	) {
		return ResponseEntity.ok(faqCategoryService.createFaqCategory(request));
	}
}
