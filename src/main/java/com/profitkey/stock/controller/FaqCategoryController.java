package com.profitkey.stock.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.profitkey.stock.annotation.ApiErrorExceptions;
import com.profitkey.stock.dto.response.faq.FaqCategoryListResponse;
import com.profitkey.stock.exception.docs.faqcategory.FaqCategoryExceptionDocs;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.faq.FaqCategoryCreateRequest;
import com.profitkey.stock.dto.request.faq.FaqCategoryUpdateRequest;
import com.profitkey.stock.dto.response.faq.FaqCategoryResponse;
import com.profitkey.stock.exception.docs.faqcategory.FaqCategoryUpdateExceptionDocs;
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

	/**
	 * 카테고리 생성 API
	 * @param request FAQ 카테고리 생성 요청 정보
	 * @return FAQ 카테고리 생성 결과 정보
	 */
	@PostMapping("")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CATEGORY_CREATE, description = SwaggerDocs.DESCRIPTION_FAQ_CATEGORY_CREATE)
	@ApiErrorExceptions(FaqCategoryExceptionDocs.class)
	public ResponseEntity<FaqCategoryResponse> createFaqCategory(@RequestBody FaqCategoryCreateRequest request) {
		return ResponseEntity.ok(faqCategoryService.createFaqCategory(request));
	}

	/**
	 * 카테고리 항목 조회 API
	 * @return FAQ 카테고리 리스트 정보
	 */
	@GetMapping("/getcategorylist")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CATEGORY_LIST, description = SwaggerDocs.DESCRIPTION_FAQ_CATEGORY_LIST)
	public ResponseEntity<FaqCategoryListResponse> getCategoryList() {
		return ResponseEntity.ok(faqCategoryService.getCategoryList());
	}

	/**
	 * 카테고리  항목 수정 API
	 */
	@PutMapping("")
	@Operation(summary = SwaggerDocs.SUMMARY_FAQ_CATEGORY_UPDATE, description = SwaggerDocs.DESCRIPTION_FAQ_CATEGORY_UPDATE)
	@ApiErrorExceptions(FaqCategoryUpdateExceptionDocs.class)
	public ResponseEntity<FaqCategoryResponse> updateCategory(@RequestBody FaqCategoryUpdateRequest request) {
		return ResponseEntity.ok(faqCategoryService.updateCategory(request));
	}

}
