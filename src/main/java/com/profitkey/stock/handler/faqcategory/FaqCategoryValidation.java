package com.profitkey.stock.handler.faqcategory;

import org.springframework.stereotype.Component;

import com.profitkey.stock.dto.request.faq.FaqCategoryRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FaqCategoryValidation {
	public static Boolean validateFaqCategoryRequest(FaqCategoryRequest request) {
		// 카테고리 이름 유효성 검사
		if (request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
			log.error("Category name is null or empty");
            return false;
		}
		// displayOrder 유효성 검사
		if (request.getDisplayOrder() < 0) {
			log.error("Invalid display order value: {}", request.getDisplayOrder());
			return false;
		}
		// isUse 유효성 검사
		if (request.getIsUse() == null) {
			log.error("Invalid isUse value: null");
			return false;
		}
		return true;
	}
}
