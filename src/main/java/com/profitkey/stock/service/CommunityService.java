package com.profitkey.stock.service;

import com.profitkey.stock.dto.request.community.CommunityRequest;
import com.profitkey.stock.dto.request.community.CommunityUpdateRequest;
import com.profitkey.stock.dto.request.community.LikeRequest;
import com.profitkey.stock.dto.response.community.CommunityResponse;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.Likes;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.community.LikesRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {
	private final CommunityRepository communityRepository;
	private final LikesRepository likesRepository;
	private final int SIZE = 10;

	@Transactional(readOnly = true)
	public Page<Community> getCommunityByStockCode(String stockCode, int page) {
		Pageable pageable = PageRequest.of(page - 1, SIZE, Sort.unsorted());
		return communityRepository.findByStockCode(stockCode, null, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Community> getCommunityById(String id, int page) {
		Pageable pageable = PageRequest.of(page - 1, SIZE, Sort.unsorted());
		return communityRepository.findByStockCode(null, id, pageable);
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
		return CommunityResponse.fromEntity(community);
	}

	@Transactional
	public CommunityResponse updateCommunity(CommunityUpdateRequest request) {
		Community community = communityRepository.findById(Long.valueOf(request.getId()))
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + request.getId()));

		community.setContent(request.getContent());
		communityRepository.save(community);
		return CommunityResponse.fromEntity(community);
	}

	@Transactional
	public void deleteCommunity(String id) {
		Long longId = Long.valueOf(id);
		communityRepository.findById(longId)
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. ID: " + id));

		communityRepository.deleteByParentId(longId);
		communityRepository.deleteById(longId);
	}

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

}
