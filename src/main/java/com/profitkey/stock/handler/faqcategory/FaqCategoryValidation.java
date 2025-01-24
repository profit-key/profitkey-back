package com.profitkey.stock.handler.faqcategory;

import org.springframework.stereotype.Component;

import com.profitkey.stock.dto.request.faq.FaqCategoryCreateRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FaqCategoryValidation {
	public Boolean validateFaqCategoryRequest(String CategoryName, Integer displayOrder, Boolean isUse) {
		// 카테고리 이름 유효성 검사
		if (CategoryName == null || CategoryName.trim().isEmpty()) {
			log.error("Category name is null or empty");
            return false;
		}
		// displayOrder 유효성 검사
		if (displayOrder == null || displayOrder < 0) {
			log.error("Invalid display order value: {}", displayOrder);
			return false;
		}
		// isUse 유효성 검사
		if (isUse == null) {
			log.error("Invalid isUse value: null");
			return false;
		}
		return true;
	}
}
