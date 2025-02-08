package com.profitkey.stock.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.mypage.FavoriteStockRepository;
import com.profitkey.stock.repository.mypage.MyPageRepository;
import com.profitkey.stock.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
	private final FavoriteStockRepository favoriteStockRepository;
	private final CommunityRepository communityRepository;
	private final UserRepository userRepository;
	private final MyPageRepository myPageRepository;

	// 📌 관심 종목

	/**
	 * 관심 종목 조회
	 */
	public List<FavoriteStock> getFavoriteStocks(Long userId) {
		return favoriteStockRepository.findByUserId(userId);
	}

	/**
	 * 관심 종목 삭제
	 */
	@Transactional
	public void deleteFavoriteStock(Long userId, String stockCode) {
		favoriteStockRepository.deleteByUserIdAndStockCode(userId, stockCode);
	}

	// 📌 댓글

	/**
	 * 사용자가 작성한 댓글 조회
	 */
	public List<MyPageCommunityResponse> getUserComments(Long userId) {
		// 사용자 ID로 작성한 댓글 목록 조회
		List<Community> communities = communityRepository.findByWriterId(userId);

		// 댓글 정보 리스트를 MyPageCommunityResponse로 변환
		return communities.stream()
			.map(c -> {
				// 사용자 닉네임을 가져오기 (여기선 예시로 userRepository를 통해 가져옴)
				String nickname = userRepository.findById(userId)
					.map(User::getNickname)  // 유저의 닉네임을 가져옴
					.orElse("알 수 없음");  // 닉네임이 없을 경우 기본값 설정

				return new MyPageCommunityResponse(
					c.getId(),
					c.getContent(),
					c.getCreatedAt().toString(),
					nickname
				);
			})
			.collect(Collectors.toList());
	}

	/**
	 * 사용자가 작성한 댓글 삭제
	 */
	@Transactional
	public void deleteUserComment(Long commentId) {
		communityRepository.deleteById(commentId);
	}

	// 📌 회원 정보

	/**
	 * 내 정보 조회
	 */
	public User getUserInfo(Long userId) {
		return userRepository.findById(userId).orElse(null);  // user가 없으면 null 반환
	}

	/**
	 * 회원 정보 수정 (닉네임, 프로필 이미지)
	 */
	@Transactional
	public User updateUserInfo(Long userId, String nickname, String profileImageUrl) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		user.setNickname(nickname);
		user.setProfileImageUrl(profileImageUrl);
		return userRepository.save(user);
	}

	/**
	 * 회원 탈퇴
	 */
	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		user.setIsDeleted(true);  // 소프트 삭제 처리
		userRepository.save(user);
	}
}
