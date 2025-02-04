package com.profitkey.stock.repository.faq;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.Faq;
import com.profitkey.stock.entity.FaqCategoryCode;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByFaqCategoryOrderByCreatedAtDesc(FaqCategoryCode category);
}
