package com.profitkey.stock.service;

import com.profitkey.stock.dto.request.community.CommunityRequest;
import com.profitkey.stock.dto.request.community.CommunityUpdateRequest;
import com.profitkey.stock.dto.request.community.LikeRequest;
import com.profitkey.stock.dto.response.community.CommunityResponse;
import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.Likes;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.community.LikesRepository;
import com.profitkey.stock.repository.mypage.UserInfoRepository;
import com.profitkey.stock.util.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {
	private final CommunityRepository communityRepository;
	private final LikesRepository likesRepository;
	private final UserInfoRepository userInfoRepository;
	private final int SIZE = 10;

	@Transactional(readOnly = true)
	public Page<CommunityResponse> getCommunityByStockCode(String stockCode, int page) {
		Pageable pageable = PageRequest.of(page - 1, SIZE, Sort.by(Sort.Direction.DESC, "id"));

		Page<Object[]> results = communityRepository.findByStockCodeWithCounts(stockCode, pageable);

		return results.map(row -> CommunityResponse.fromEntity(
			(Community)row[0],
			((Number)row[1]).longValue(),
			((Number)row[2]).longValue()
		));
	}

	@Transactional(readOnly = true)
	public Page<CommunityResponse> getCommunityById(String id, int page) {
		Pageable pageable = PageRequest.of(page - 1, SIZE, Sort.by(Sort.Direction.DESC, "id"));

		Page<Object[]> results = communityRepository.findByParentId(id, pageable);

		return results.map(row -> {
			Community community = (Community)row[0];
			long likeCount = ((Number)row[1]).longValue();

			return CommunityResponse.fromEntity(community, likeCount, 0);
		});
	}

	@Transactional
	public CommunityResponse createCommunity(CommunityRequest request) {
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String stockCode = request.getStockCode();
		int sequence = communityRepository.getNextSequence(today, stockCode);
		String id = today + stockCode + String.format("%04d", sequence);

		Community community = Community.builder()
			.id(id)
			.writerId(request.getWriterId())
			.parentId(request.getParentId())
			.content(request.getContent())
			.build();
		communityRepository.save(community);
		return CommunityResponse.fromEntity(community, 0, 0);
	}

	@Transactional
	public CommunityResponse updateCommunity(CommunityUpdateRequest request) {
		Community community = communityRepository.findById(Long.valueOf(request.getId()))
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + request.getId()));

		community.setContent(request.getContent());
		communityRepository.save(community);
		return CommunityResponse.fromEntity(community, 0, 0);
	}

	@Transactional
	public void deleteCommunity(String id) {
		Long longId = Long.valueOf(id);
		communityRepository.findById(longId)
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + id));

		communityRepository.deleteByParentId(longId);
		communityRepository.deleteById(longId);
	}

	// public void likeComment(LikeRequest request) {
	// 	Auth auth = SecurityUtil.getUser();
	// 	log.info(" id : {} ", auth.getId());
	// 	log.info(" email : {} ", auth.getEmail());
	// 	log.info(" provider : {} ", auth.getProvider());
	// 	log.info(" nickname : {} ", auth.getNickname());
	// 	Likes likes = Likes.builder()
	// 		.commentId(request.getCommentId())
	// 		.writerId(request.getUserId())
	// 		.createdAt(LocalDateTime.now())
	// 		.build();
	//
	// 	likesRepository.save(likes);
	// }

	@Transactional
	public void likeComment(LikeRequest request) {
		Auth auth = SecurityUtil.getUser();

		log.info("id : {} ", auth.getId());
		log.info("provider : {} ", auth.getProvider());

		UserInfo userInfo = userInfoRepository.findById(auth.getId())
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + auth.getId()));

		log.info("nickname : {} ", userInfo.getNickname());

		Likes likes = Likes.builder()
			.commentId(request.getCommentId())
			.writerId(request.getUserId())
			.createdAt(LocalDateTime.now())
			.build();

		likesRepository.save(likes);
	}

	// todo : 댓글삭제할때 좋아요도 삭제되야할듯
	public void unlikeComment(LikeRequest request) {
		likesRepository.deleteByCommentIdAndWriterId(request.getCommentId(), request.getUserId());
	}

}
