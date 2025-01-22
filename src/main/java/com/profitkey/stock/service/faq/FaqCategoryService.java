package com.profitkey.stock.service.faq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.profitkey.stock.dto.request.faq.FaqCategoryRequest;
import com.profitkey.stock.dto.response.faq.FaqCategoryResponse;
import com.profitkey.stock.entity.FaqCategoryCode;
import com.profitkey.stock.exception.docs.faqcategory.FaqCategoryExceptionDocs;
import com.profitkey.stock.exception.testexception.faq.InvalidTestException;
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
   public FaqCategoryResponse createFaqCategory(FaqCategoryRequest request) {

       if(!faqCategoryValidation.validateFaqCategoryRequest(request)){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다.");
       }
       // 현재 최대 displayOrder 값을 조회
       Integer maxDisplayOrder = faqCategoryRepository.findMaxDisplayOrder();
       
       // 최대값이 없으면(첫 데이터면) 1, 있으면 최대값 + 1
       Integer newDisplayOrder = (maxDisplayOrder == null) ? 1 : maxDisplayOrder + 1;
       
       FaqCategoryCode faqCategoryCode = FaqCategoryCode.builder()
           .categoryName(request.getCategoryName())
           .displayOrder(request.getDisplayOrder() == null ? newDisplayOrder : request.getDisplayOrder())  // 자동 계산된 값 사용
           .published(request.getIsUse() == null ? true : request.getIsUse())
           .build();
           
       FaqCategoryCode savedCategory = faqCategoryRepository.save(faqCategoryCode);
       FaqCategoryResponse res = FaqCategoryResponse.of(savedCategory.getId(), savedCategory.getCategoryName(), savedCategory.getDisplayOrder(), savedCategory.getPublished());
       return res;
   }
}
