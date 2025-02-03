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
		return true;
	}
}
