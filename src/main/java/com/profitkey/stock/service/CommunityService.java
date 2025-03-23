package com.profitkey.stock.service;

import com.profitkey.stock.annotation.AuthCheck;
import com.profitkey.stock.dto.community.CommentPopularityDto;
import com.profitkey.stock.dto.request.community.CommunityRequest;
import com.profitkey.stock.dto.request.community.CommunityUpdateRequest;
import com.profitkey.stock.dto.request.community.LikeRequest;
import com.profitkey.stock.dto.response.community.CommunityResponse;
import com.profitkey.stock.entity.CommSort;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.Likes;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.exception.testexception.mypage.UnauthorizedException;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.community.LikesRepository;
import com.profitkey.stock.repository.mypage.UserInfoRepository;
import com.profitkey.stock.util.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
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
	private final S3UploadService s3UploadService;
	private final int SIZE = 10;

	@Transactional(readOnly = true)
	public Page<CommunityResponse> getCommunityByStockCode(String stockCode, int page, CommSort order) {
		Long userId = SecurityUtil.getCurrentUserId();
		Sort sort;
		if (order == CommSort.POPULAR) {
			sort = Sort.by(Sort.Direction.DESC, "likeCount"); // 인기순
		} else if (order == CommSort.LATEST) {
			sort = Sort.by(Sort.Direction.DESC, "createdAt"); // 최신순
		} else {
			sort = Sort.by(Sort.Direction.DESC, "createdAt"); // 최신순
		}

		Pageable pageable = PageRequest.of(page - 1, SIZE, sort);

		Page<Object[]> results = communityRepository.findByStockCodeWithCounts(stockCode, userId, pageable);

		return results.map(row -> {
			Community community = (Community)row[0];
			boolean liked = (Boolean)row[1];
			long likeCount = ((Number)row[2]).longValue();
			long replieCount = ((Number)row[3]).longValue();
			
			UserInfo writer = userInfoRepository.findById(community.getWriterId())
				.orElseThrow(() -> new RuntimeException("Writer not found"));
			return CommunityResponse.fromEntity(community, writer, liked, likeCount, replieCount, s3UploadService);
		});
	}

	@Transactional(readOnly = true)
	public Page<CommunityResponse> getCommunityById(String id, int page) {
		Long userId = SecurityUtil.getCurrentUserId();
		Pageable pageable = PageRequest.of(page - 1, SIZE, Sort.by(Sort.Direction.DESC, "id"));

		Page<Object[]> results = communityRepository.findByParentId(id, userId, pageable);

		return results.map(row -> {
			Community community = (Community)row[0];
			boolean liked = (Boolean)row[1];
			long likeCount = ((Number)row[2]).longValue();

			UserInfo writer = userInfoRepository.findById(community.getWriterId())
				.orElseThrow(() -> new RuntimeException("Writer not found"));
			return CommunityResponse.fromEntity(community, writer, liked, likeCount, 0, s3UploadService);
		});
	}

	@Transactional
	public CommunityResponse createCommunity(CommunityRequest request) {
		// 유저가 존재하지 않거나 삭제된 유저인 경우 예외 처리
		UserInfo userInfo = userInfoRepository.findById(request.getWriterId())
			.orElseThrow(() -> new UnauthorizedException("로그인하지 않았거나 삭제된 유저는 댓글을 달 수 없습니다."));

		if (userInfo.getIsDeleted()) {
			throw new UnauthorizedException("로그인하지 않았거나 삭제된 유저는 댓글을 달 수 없습니다.");
		}

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

		// writerId로 UserInfo 객체를 조회
		return CommunityResponse.fromEntity(community, userInfo, false, 0, 0, s3UploadService);
	}

	@Transactional
	public CommunityResponse updateCommunity(CommunityUpdateRequest request) {
		Community community = communityRepository.findById(Long.valueOf(request.getId()))
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + request.getId()));

		community.setContent(request.getContent());
		communityRepository.save(community);

		// writerId로 UserInfo 객체를 조회
		UserInfo writer = userInfoRepository.findById(community.getWriterId())
			.orElseThrow(() -> new RuntimeException("Writer not found"));

		return CommunityResponse.fromEntity(community, writer, false, 0, 0, s3UploadService);
	}

	@Transactional
	public void deleteCommunity(String id) {
		Long longId = Long.valueOf(id);
		communityRepository.findById(longId)
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + id));

		communityRepository.deleteByParentId(longId);
		communityRepository.deleteById(longId);
	}

	@Transactional
	@AuthCheck
	public void likeComment(LikeRequest request) {
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

	// 좋아요 개수를 기준으로 인기순 정렬
	@Transactional(readOnly = true)
	public List<CommentPopularityDto> getMostLikedComments(String stockCode) {
		List<Object[]> results = likesRepository.findMostLikedCommentsByStockCode(stockCode);
		return results.stream()
			.map(result -> new CommentPopularityDto((String)result[0], (Long)result[1]))
			.collect(Collectors.toList());
	}

	// 댓글 생성 시간을 기준으로 최신순 정렬
	@Transactional(readOnly = true)
	public List<CommentPopularityDto> getLatestComments(String stockCode) {
		List<Object[]> results = communityRepository.findLatestCommentsByStockCode(stockCode);
		return results.stream()
			.map(result -> new CommentPopularityDto((String)result[0], null)) // 최신순에서는 likeCount는 null 처리
			.collect(Collectors.toList());
	}
}