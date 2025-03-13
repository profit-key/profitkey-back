package com.profitkey.stock.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.profitkey.stock.dto.response.mypage.FavoriteStockResponse;
import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.dto.response.mypage.UserInfoResponse;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.StockCode;
import com.profitkey.stock.entity.UploadFile;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.repository.community.CommunityRepository;
import com.profitkey.stock.repository.mypage.FavoriteStockRepository;
import com.profitkey.stock.repository.mypage.UserInfoRepository;
import com.profitkey.stock.repository.stock.StockCodeRepository;
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
	private final S3UploadService s3UploadService;
	private final StockCodeRepository stockCodeRepository;

	// 📌 회원 정보

	/**
	 * 내 정보 조회
	 */
	public UserInfoResponse getUserInfo(Long userId) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// 프로필 이미지가 존재하면 S3 URL을 반환하고, 없으면 빈 문자열 처리
		String profileImageUrl = "";  // 기본값으로 빈 문자열 할당

		if (userInfo.getProfileImage() != null && !userInfo.getProfileImage().isEmpty()) {
			profileImageUrl = s3UploadService.getFileUrl(userInfo.getProfileImage());
		}

		return UserInfoResponse.fromEntity(userInfo, profileImageUrl);  // S3 URL을 반환
	}

	/**
	 * 회원 정보 수정 (닉네임, 프로필 이미지)
	 */

	// 📌 회원 정보 수정 (닉네임, 프로필사진 수정 분리)

	//닉네임 수정
	@Transactional
	public UserInfoResponse updateNickname(Long userId, String newNickname) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		userInfo.setNickname(newNickname);

		return UserInfoResponse.fromEntity(userInfo);
	}

	@Transactional
	public UserInfoResponse updateProfileImage(Long userId, MultipartFile profileImage) throws IOException,
		IOException {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// 기존 프로필 이미지 삭제 (기본 이미지가 아니라면)
		if (userInfo.getProfileImage() != null) {
			s3UploadService.deleteFile(userInfo.getProfileImage());
		}

		// 새 이미지 업로드
		UploadFile uploadedFile = s3UploadService.uploadSingleFile(profileImage);
		userInfo.setProfileImage(uploadedFile.getFileKey());

		// 업로드된 이미지의 URL 가져오기
		String imageUrl = s3UploadService.getFileUrl(uploadedFile.getFileKey());

		return UserInfoResponse.fromEntity(userInfo, imageUrl);
	}

	@Transactional
	public UserInfoResponse deleteProfileImage(Long userId) {
		UserInfo userInfo = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		// 기존 프로필 이미지가 존재하면 S3에서 삭제
		if (userInfo.getProfileImage() != null && !userInfo.getProfileImage().isEmpty()) {
			s3UploadService.deleteFile(userInfo.getProfileImage());
		}

		// 프로필 이미지를 빈 문자열("")로 설정 (기본 이미지로 변경)
		userInfo.setProfileImage("");

		return UserInfoResponse.fromEntity(userInfo);
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
	/*
	/* 관심 종목 찜하기
	 */
	@Transactional
	public boolean addFavoriteStock(Long userId, String stockCode) {
		UserInfo user = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

		StockCode stockCodeEntity = stockCodeRepository.findByStockCode(stockCode);
		if (stockCodeEntity == null) {
			throw new IllegalArgumentException("Stock code not found for: " + stockCode);
		}

		FavoriteStock favoriteStock = FavoriteStock.builder()
			.user(user)
			.stockCode(stockCodeEntity)
			.build();

		favoriteStockRepository.save(favoriteStock);

		// 찜 유무 반환
		return favoriteStockRepository.existsByUser_UserIdAndStockCode_StockCode(userId, stockCode);
	}

	private UserInfo getUserById(Long userId) {
		// UserInfo를 userId로 조회하는 로직 구현
		return new UserInfo(); // 예시로 새로운 UserInfo 객체 리턴 (실제 구현 필요)
	}

	/**
	 * 관심 종목 조회
	 */
	public List<FavoriteStockResponse> getFavoriteStocks(Long userId) {
		List<FavoriteStock> favoriteStocks = favoriteStockRepository.findByUser_UserId(userId);

		return favoriteStocks.stream()
			.map(favoriteStock -> {
				boolean isLiked = favoriteStockRepository.existsByUser_UserIdAndStockCode_StockCode(
					userId, favoriteStock.getStockCode().getStockCode()
				);
				return FavoriteStockResponse.fromEntity(favoriteStock, isLiked);
			})
			.collect(Collectors.toList());
	}

	// 종목 상세 좋아요 유무 조회
	public boolean isFavoriteStock(Long userId, String stockCode) {
		return favoriteStockRepository.existsByUser_UserIdAndStockCode_StockCode(userId, stockCode);
	}

	/**
	 * 관심 종목 삭제
	 */
	@Transactional
	public void deleteFavoriteStock(Long userId, String stockCodeString) {
		// stockCodeString을 기반으로 StockCode 객체를 찾기
		StockCode stockCode = favoriteStockRepository.findStockCodeByStockCode(stockCodeString);

		if (stockCode != null) {
			// StockCode 객체를 기반으로 관심 종목 삭제
			favoriteStockRepository.deleteByUserIdAndStockCode(userId, stockCode);
		} else {
			throw new RuntimeException("Stock code not found: " + stockCodeString);
		}
	}
}
