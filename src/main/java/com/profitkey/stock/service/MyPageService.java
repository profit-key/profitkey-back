package com.profitkey.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.UserRepository;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.mypage.FavoriteStockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
	private final FavoriteStockRepository favoriteStockRepository;
	private final CommunityRepository communityRepository;
	private final UserRepository userRepository;

	// 📌 관심 종목

	/** 관심 종목 조회 */
	public List<FavoriteStock> getFavoriteStocks(Long userId) {
		return favoriteStockRepository.findByUserId(userId);
	}

	/** 관심 종목 삭제 */
	@Transactional
	public void deleteFavoriteStock(Long userId, String stockCode) {
		favoriteStockRepository.deleteByUserIdAndStockCode(userId, stockCode);
	}

	// 📌 댓글

	/** 사용자가 작성한 댓글 조회 */
	public List<Community> getUserComments(Long userId) {
		return communityRepository.findByWriterId(userId);
	}

	/** 사용자가 작성한 댓글 삭제 */
	@Transactional
	public void deleteUserComment(Long commentId) {
		communityRepository.deleteById(commentId);
	}

	// 📌 회원 정보

	/**  내 정보 조회 */
	public User getUserInfo(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
	}

	/**  회원 정보 수정 (닉네임, 프로필 이미지) */
	@Transactional
	public User updateUserInfo(Long userId, String nickname, String profileImageUrl) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		user.setNickname(nickname);
		user.setProfileImageUrl(profileImageUrl);
		return userRepository.save(user);
	}

	/**  회원 탈퇴 */
	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		user.setIsDeleted(true);  // 소프트 삭제 처리
		userRepository.save(user);
	}
}
