package com.profitkey.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.profitkey.stock.dto.request.announcement.AnnouncementCreateRequest;
import com.profitkey.stock.dto.response.announcement.AnnouncementCreateResponse;
import com.profitkey.stock.dto.response.announcement.AnnouncementListItem;
import com.profitkey.stock.dto.response.announcement.AnnouncementListResponse;
import com.profitkey.stock.dto.response.announcement.AnnouncementResponse;
import com.profitkey.stock.entity.Announcement;
import com.profitkey.stock.handler.PagenationHandler;
import com.profitkey.stock.repository.AnnouncementRepository;
import com.profitkey.stock.util.DateTimeUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {

	@Autowired
	private final AnnouncementRepository announcementRepository;
	private final PagenationHandler pagenationHandler;

	@Transactional
	public AnnouncementCreateResponse createAnnouncement(Boolean published, String title, String content) {
		Announcement announcement = Announcement.builder().title(title).published(published).content(content).build();
		announcementRepository.save(announcement);
		AnnouncementCreateResponse res = AnnouncementCreateResponse.builder()
			.id(announcement.getId())
			.title(title)
			.published(announcement.getPublished())
			.content(content)
			.build();
		return res;
	}

	@Transactional
	public AnnouncementListResponse getAnnouncementList(int page, int size) {
		// 페이지 설정
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

		Page<Announcement> announcements = announcementRepository.findAll(pageable);

		AnnouncementListItem[] announcementListItems = announcements.getContent()
			.stream()
			.map(announce -> AnnouncementListItem.builder()
				.id(announce.getId())
				.title(announce.getTitle())
				.createdAt(DateTimeUtil.formatDate(announce.getCreatedAt()))
				.build())
			.toArray(AnnouncementListItem[]::new);

		return AnnouncementListResponse.builder()
			.announcements(announcementListItems)
			.pagination(
				pagenationHandler.setPagenation(announcements.getTotalPages(), announcements.getTotalElements(), page))
			.build();
	}

	@Transactional
	public AnnouncementResponse getAnnouncement(Long id) {
		Announcement announce = announcementRepository.findByIdAndPublishedTrue(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다. ID: " + id));
		return AnnouncementResponse.builder()
			.id(announce.getId())
			.title(announce.getTitle())
			.content(announce.getContent())
			.published(announce.getPublished())
			.createdAt(DateTimeUtil.formatDate(announce.getCreatedAt()))
			.build();
	}
}
