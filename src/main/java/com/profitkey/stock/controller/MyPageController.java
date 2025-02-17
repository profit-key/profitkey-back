package com.profitkey.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.dto.request.mypage.UserUpdateRequest;
import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.dto.response.mypage.UserInfoResponse;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	/*
	 * 관심 종목
	 */
	// 📌 관심 종목 조회
	@GetMapping("/favorite-stocks/{userId}")
	public ResponseEntity<List<FavoriteStock>> getFavoriteStocks(@PathVariable Long userId) {
		List<FavoriteStock> favoriteStocks = myPageService.getFavoriteStocks(userId);
		return ResponseEntity.ok(favoriteStocks);
	}

	// 📌 관심 종목 삭제
	@DeleteMapping("/favorite-stocks/{userId}/{stockCode}")
	public ResponseEntity<Void> deleteFavoriteStock(@PathVariable Long userId, @PathVariable String stockCode) {
		myPageService.deleteFavoriteStock(userId, stockCode);
		return ResponseEntity.noContent().build();
	}

	/*
	 * 댓글 (community)
	 */
	// 📌 사용자가 작성한 댓글 조회
	@GetMapping("/comments/{userId}")
	public ResponseEntity<List<MyPageCommunityResponse>> getUserComments(@PathVariable Long userId) {
		List<MyPageCommunityResponse> comments = myPageService.getUserComments(userId);
		return ResponseEntity.ok(comments);
	}

	// 📌 사용자가 작성한 댓글 삭제
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> deleteUserComment(@PathVariable Long commentId) {
		myPageService.deleteUserComment(commentId);
		return ResponseEntity.noContent().build();
	}

	/*
	 * 내 정보
	 */
	// 📌 내 정보 조회
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long userId) {
		UserInfo userInfo = myPageService.getUserInfo(userId);

		if (userInfo != null) {
			UserInfoResponse response = UserInfoResponse.builder()
				.email(userInfo.getAuth().getEmail())  //  Auth에서 이메일 가져오기
				.nickname(userInfo.getNickname())  // UserInfo에서 닉네임 가져오기
				.profileImageUrl(userInfo.getProfileImage())  // UserInfo에서 프로필 이미지 가져오기
				.build();
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// 📌 회원 정보 수정 (닉네임, 프로필 이미지 변경)
	@PatchMapping("/user/{userId}") // email은 수정 불가
	public ResponseEntity<UserInfo> updateUserInfo(@PathVariable Long userId,
		@RequestBody UserUpdateRequest request) {
		UserInfo updatedUser = myPageService.updateUserInfo(userId, request.getNickname(),
			request.getProfileImageUrl());
		return ResponseEntity.ok(updatedUser);
	}

	// 📌 회원 탈퇴
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		myPageService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}
}
