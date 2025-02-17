package com.profitkey.stock.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.dto.request.mypage.UserUpdateRequest;
import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.dto.response.mypage.UserInfoResponse;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.mypage.FavoriteStockRepository;
import com.profitkey.stock.repository.mypage.UserInfoRepository;
import com.profitkey.stock.repository.user.AuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
	private final FavoriteStockRepository favoriteStockRepository;
	private final CommunityRepository communityRepository;
	private final UserInfoRepository userInfoRepository;
	private final AuthRepository authRepository;

	// 📌 회원 정보

	/**
	 * 내 정보 조회
	 */
	public UserInfoResponse getUserInfo(Long userId) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		return UserInfoResponse.fromEntity(userInfo);
	}

	/**
	 * 회원 정보 수정 (닉네임, 프로필 이미지)
	 */

	// 📌 회원 정보 수정 (닉네임, 프로필 이미지 변경)
	@Transactional
	public UserInfoResponse updateUserInfo(Long userId, UserUpdateRequest request) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// 닉네임이 null이 아니면 업데이트
		if (request.getNickname() != null) {
			userInfo.setNickname(request.getNickname());
		}

		// 프로필 이미지 업데이트 (null도 허용)
		if (request.getProfileImageUrl() != null) {
			userInfo.setProfileImage(request.getProfileImageUrl());
		}

		return UserInfoResponse.fromEntity(userInfo);  // 업데이트된 정보 반환
	}

	/**
	 * 회원 탈퇴 (UserInfo 소프트 딜리트, Auth 삭제)
	 */
	@Transactional
	public void deleteUser(Long userId) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// 소프트 삭제 처리
		userInfo.setIsDeleted(true);
		userInfo.setDeletedAt(LocalDateTime.now());

		// Auth 엔티티 삭제
		if (userInfo.getAuth() != null) {
			authRepository.delete(userInfo.getAuth());
		}
	}

	//탈퇴하면 30일 내 재가입 불가능
	public void checkRejoinRestriction(String email) {
		Optional<UserInfo> deletedUser = userInfoRepository.findByAuth_EmailAndIsDeleted(email, true);

		if (deletedUser.isPresent()) {
			LocalDateTime deletedAt = deletedUser.get().getDeletedAt();
			LocalDateTime now = LocalDateTime.now();

			if (deletedAt.plusDays(30).isAfter(now)) {
				throw new RuntimeException("회원 탈퇴 후 30일 동안 재가입할 수 없습니다.");
			}
		}
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
				// UserInfo에서 닉네임 조회
				String nickname = userInfoRepository.findById(userId)
					.map(UserInfo::getNickname)
					.orElse("알 수 없음");

				return new MyPageCommunityResponse(
					c.getId(),
					c.getContent(),
					c.getCreatedAt().toString(),
					nickname
				);
			})
			.collect(Collectors.toList());
	}

	// 📌 관심 종목

	/**
	 * 관심 종목 조회
	 */
	public List<FavoriteStock> getFavoriteStocks(Long userId) {
		return favoriteStockRepository.findByUser_UserId(userId);
	}

	/**
	 * 관심 종목 삭제
	 */
	@Transactional
	public void deleteFavoriteStock(Long userId, String stockCode) {
		favoriteStockRepository.deleteByUserIdAndStockCode(userId, stockCode);
	}
}
