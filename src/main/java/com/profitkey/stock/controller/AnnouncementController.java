package com.profitkey.stock.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.PaginationRequest;
import com.profitkey.stock.dto.request.announcement.AnnouncementCreateRequest;
import com.profitkey.stock.dto.response.announcement.AnnouncementCreateResponse;
import com.profitkey.stock.dto.response.announcement.AnnouncementListResponse;
import com.profitkey.stock.dto.response.announcement.AnnouncementResponse;
import com.profitkey.stock.service.AnnouncementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
@RequiredArgsConstructor
public class AnnouncementController {
	private final AnnouncementService announcementService;

	/**
	 * 공지사항 생성 API
	 * @return 생성한 공지사항 정보
	 */
	@PostMapping("")
	@Operation(summary = SwaggerDocs.SUMMARY_ANNOUNCE_CREATE, description = SwaggerDocs.DESCRIPTION_ANNOUNCE_CREATE)
	public ResponseEntity<AnnouncementCreateResponse> createAnnounce(@RequestBody AnnouncementCreateRequest request) {
		AnnouncementCreateResponse result = announcementService.createAnnouncement(request.getPublished(),
			request.getTitle(), request.getContent());
		return ResponseEntity.ok(result);
	}

	/**
	 * 공지사항 목록 조회 API
	 * @return 조회한 공지사항 목록(페이징)
	 */
	@GetMapping("/list")
	@Operation(summary = SwaggerDocs.SUMMARY_ANNOUNCE_LIST, description = SwaggerDocs.DESCRIPTION_ANNOUNCE_LIST)
	public ResponseEntity<AnnouncementListResponse> getAnnounceList(
		@ParameterObject @ModelAttribute PaginationRequest request) {
		AnnouncementListResponse response = announcementService.getAnnouncementList(request.getPage(),
			request.getSize());
		return ResponseEntity.ok(response);
	}

	/**
	 * 공지사항 상세 조회
	 * @return 조회한 공지사항 상세 내역
	 */
	@GetMapping("/{id}")
	@Operation(summary = SwaggerDocs.SUMMARY_ANNOUNCE_INFO, description = SwaggerDocs.DESCRIPTION_ANNOUNCE_INFO)
	public ResponseEntity<AnnouncementResponse> getAnnouncement(
		@Parameter(description = "공지사항 ID", required = true)
		@PathVariable Long id
	) {
		AnnouncementResponse response = announcementService.getAnnouncement(id);
		return ResponseEntity.ok(response);
	}
}
