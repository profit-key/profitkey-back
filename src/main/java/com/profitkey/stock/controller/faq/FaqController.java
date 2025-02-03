package com.profitkey.stock.controller.faq;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profitkey.stock.dto.response.faq.FaqCreateResponse;
import com.profitkey.stock.service.faq.FaqService;
import com.profitkey.stock.dto.request.faq.FaqCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/faq")
@Slf4j
@RequiredArgsConstructor
public class FaqController {
	private final FaqService faqService;

	/**
	 * FAQ 생성 API
	 */
	@Operation(summary = "FAQ 파일 업로드 테스트", description = "FAQ 작성 시 파일 업로드 기능을 테스트합니다.")
	@PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<FaqCreateResponse> createFaq(@Valid @ModelAttribute FaqCreateRequest request) throws
		IOException {
		FaqCreateResponse result = faqService.createFaq(request.getFiles(), request.getPublished(),
			request.getFaqCategoryName(), request.getTitle(), request.getContent());
		return ResponseEntity.ok(result);
	}
}
