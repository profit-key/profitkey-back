package com.profitkey.stock.service.faq;

import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.profitkey.stock.dto.request.faq.FaqCategoryCreateRequest;
import com.profitkey.stock.dto.request.faq.FaqCategoryUpdateRequest;
import com.profitkey.stock.dto.response.faq.FaqCategoryListResponse;
import com.profitkey.stock.dto.response.faq.FaqCategoryResponse;
import com.profitkey.stock.entity.FaqCategoryCode;
import com.profitkey.stock.handler.faqcategory.FaqCategoryValidation;
import com.profitkey.stock.repository.faq.FaqCategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqCategoryService {
	private final FaqCategoryRepository faqCategoryRepository;
	private final FaqCategoryValidation faqCategoryValidation;

	@Transactional
	public FaqCategoryResponse createFaqCategory(FaqCategoryCreateRequest request) {
        // validation request
		if (!faqCategoryValidation.validateFaqCategoryRequest(request.getCategoryName(), request.getDisplayOrder(), request.getIsUse())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다.");
		}
		// 현재 최대 displayOrder 값을 조회
		Integer maxDisplayOrder = faqCategoryRepository.findMaxDisplayOrder();

		// 최대값이 없으면(첫 데이터면) 1, 있으면 최대값 + 1
		Integer newDisplayOrder = (maxDisplayOrder == null) ? 1 : maxDisplayOrder + 1;

		FaqCategoryCode faqCategoryCode = FaqCategoryCode.builder()
			.categoryName(request.getCategoryName())
			.displayOrder(
				request.getDisplayOrder() == null ? newDisplayOrder : request.getDisplayOrder())  // 자동 계산된 값 사용
			.published(request.getIsUse() == null ? true : request.getIsUse())
			.build();

		FaqCategoryCode savedCategory = faqCategoryRepository.save(faqCategoryCode);
		FaqCategoryResponse res = FaqCategoryResponse.of(savedCategory.getId(), savedCategory.getCategoryName(),
			savedCategory.getDisplayOrder(), savedCategory.getPublished());
		return res;
	}

	@Transactional
	public FaqCategoryListResponse getCategoryList() {
		List<FaqCategoryCode> categoryDataList = faqCategoryRepository.findAll();
		FaqCategoryListResponse res = FaqCategoryListResponse.builder()
			.categoryList(categoryDataList.stream()
				.map(faqCategory -> FaqCategoryResponse.of(faqCategory.getId(), faqCategory.getCategoryName(), faqCategory.getDisplayOrder(), faqCategory.getPublished()))
				.collect(Collectors.toList()))
			.build();
		return res;
	}

	@Transactional
	public FaqCategoryResponse updateCategory(FaqCategoryUpdateRequest request) {
        // validation request
		if (!faqCategoryValidation.validateFaqCategoryRequest(request.getCategoryName(), request.getDisplayOrder(), request.getIsUse())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 값이 입력되었습니다.");
		}
		FaqCategoryCode faqCategoryCode = faqCategoryRepository.findById(request.getCategoryId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 데이터가 존재하지 않습니다. id: " + request.getCategoryId()));
        
        // update faqCategoryCode
        faqCategoryCode = FaqCategoryCode.builder()
            .categoryName(request.getCategoryName())
            .displayOrder(request.getDisplayOrder())
            .published(request.getIsUse())
            .build();
		FaqCategoryCode savedCategory = faqCategoryRepository.save(faqCategoryCode);
		FaqCategoryResponse res = FaqCategoryResponse.of(savedCategory.getId(), savedCategory.getCategoryName(), savedCategory.getDisplayOrder(), savedCategory.getPublished());
		return res;
	}
}
