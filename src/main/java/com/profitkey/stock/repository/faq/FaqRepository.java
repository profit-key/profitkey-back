package com.profitkey.stock.repository.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.profitkey.stock.entity.Faq;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	@Query("SELECT f FROM Faq f ORDER BY f.createdAt DESC")
	List<Faq> findAllOrderByCreatedAtDesc();
}
