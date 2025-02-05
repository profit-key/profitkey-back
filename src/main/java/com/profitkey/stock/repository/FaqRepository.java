package com.profitkey.stock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	Page<Faq> findByPublishedTrueOrderByCreatedAtDesc(Pageable pageable);

	Optional<Faq> findByIdAndPublishedTrue(Long id);
}
