package com.profitkey.stock.repository.faq;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.profitkey.stock.entity.FaqCategoryCode;

public interface FaqCategoryRepository extends JpaRepository<FaqCategoryCode, Long> {
    Optional<FaqCategoryCode> findByCategoryName(String categoryName);
    @Query("SELECT MAX(f.displayOrder) FROM FaqCategoryCode f")
    Integer findMaxDisplayOrder();
}