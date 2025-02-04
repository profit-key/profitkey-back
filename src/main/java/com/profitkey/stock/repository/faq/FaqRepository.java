package com.profitkey.stock.repository.faq;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.profitkey.stock.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	@Query("SELECT f FROM Faq f ORDER BY f.createdAt DESC")
	Page<Faq> findAllOrderByCreatedAtDesc(Pageable pageable);
}
