package com.profitkey.stock.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.annotation.AuthCheck;
import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.community.CommunityRequest;
import com.profitkey.stock.dto.request.community.CommunityUpdateRequest;
import com.profitkey.stock.dto.request.community.LikeRequest;
import com.profitkey.stock.dto.response.community.CommunityResponse;
import com.profitkey.stock.service.CommunityService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

	private final CommunityService communityService;

	/**
	 * 커뮤니티 댓글 목록 조회 API
	 * @param stockCode 종목코드
	 * @param page 페이지번호
	 * @return 조회한 데이터 목록(페이징)
	 */
	@GetMapping("/{stockCode}")
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_LIST, description = SwaggerDocs.DESCRIPTION_COMMUNITY_LIST)
	public ResponseEntity<Page<CommunityResponse>> getCommunityList(
		@PathVariable String stockCode,
		@RequestParam(defaultValue = "1") int page) { // 기본값 설정 (1페이지)

		Page<CommunityResponse> communityPage = communityService.getCommunityByStockCode(stockCode, page);
		return ResponseEntity.ok(communityPage);
	}

	/**
	 * 커뮤니티 댓글 상세 조회 API
	 * @param id 식별자 yyyyMMdd(8) + 종목코드(6) + 시퀀스(4)
	 * @param page 페이지번호
	 * @return 조회한 댓글 + 대댓글 목록(페이징)
	 */
	@GetMapping("/detail")
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_DETAIL, description = SwaggerDocs.DESCRIPTION_COMMUNITY_DETAIL)
	public ResponseEntity<Page<CommunityResponse>> getCommunity(
		@RequestParam String id,
		@RequestParam int page // PathVariable 대신 RequestParam 사용
	) {
		Page<CommunityResponse> communityPage = communityService.getCommunityById(id, page);
		return ResponseEntity.ok(communityPage);
	}

	/**
	 * 커뮤니티 댓글 등록 API
	 * @param request 커뮤니티 댓글 입력 정보
	 * @return 등록된 데이터
	 */
	@PostMapping
	@AuthCheck
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_CREATE, description = SwaggerDocs.DESCRIPTION_COMMUNITY_CREATE)
	public ResponseEntity<CommunityResponse> createCommunity(@RequestBody CommunityRequest request) {
		return ResponseEntity.ok(communityService.createCommunity(request));
	}

	/**
	 * 커뮤니티 댓글 수정 API
	 * @param request 입력항목
	 * @return 수정된 데이터
	 */
	@PutMapping("")
	@AuthCheck
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_UPDATE, description = SwaggerDocs.DESCRIPTION_COMMUNITY_UPDATE)
	public ResponseEntity<CommunityResponse> updateCommunity(@RequestBody CommunityUpdateRequest request) {
		CommunityResponse response = communityService.updateCommunity(request);
		return ResponseEntity.ok(response);
	}

	/**
	 * 커뮤니티 댓글 삭제 API
	 * @param id 식별자 yyyyMMdd(8) + 종목코드(6) + 시퀀스(4)
	 * @return 성공여부
	 */
	@DeleteMapping("/{id}")
	@AuthCheck
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_DELETE, description = SwaggerDocs.DESCRIPTION_COMMUNITY_DELETE)
	public ResponseEntity<Void> deleteCommunity(@PathVariable String id) {
		communityService.deleteCommunity(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/like")
	@AuthCheck
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_LIKE, description = SwaggerDocs.DESCRIPTION_COMMUNITY_LIKE)
	public ResponseEntity<Void> likeComment(@RequestBody LikeRequest request) {
		if (request.isLiked()) {
			communityService.likeComment(request);
		} else {
			communityService.unlikeComment(request);
		}

		return ResponseEntity.ok().build();
	}

	// 최신순 정렬 API
	// @GetMapping("/latest")
	// @Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_LATEST, description = SwaggerDocs.DESCRIPTION_COMMUNITY_LATEST)
	// public ResponseEntity<Page<CommunityResponse>> getLatestComments(
	// 	@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
	//
	// 	Page<Object[]> result = communityService.getLatestComments(pageable);
	// 	Page<CommunityResponse> response = result.map(obj ->
	// 		CommunityResponse.fromEntity((Community)obj[0], (long)obj[1], 0L)
	// 	);
	// 	return ResponseEntity.ok(response);
	// }

	// 인기순 정렬 API
	@GetMapping("/popular")
	@Operation(summary = SwaggerDocs.SUMMARY_COMMUNITY_LIKES, description = SwaggerDocs.DESCRIPTION_COMMUNITY_LIKES)
	public Page<Object[]> getPopularComments(Pageable pageable) {
		return communityService.getPopularComments(pageable);
	}
}