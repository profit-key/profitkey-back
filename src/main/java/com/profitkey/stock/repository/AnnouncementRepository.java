package com.profitkey.stock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	Page<Announcement> findByPublishedTrueOrderByCreatedAtDesc(Pageable pageable);

    Optional<Announcement> findById(Long id);

    Optional<Announcement> findByIdAndPublishedTrue(Long id);
}
