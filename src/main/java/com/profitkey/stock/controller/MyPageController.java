package com.profitkey.stock.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	// 📌 관심 종목 조회
	@GetMapping("/favorite-stocks")
	public ResponseEntity<List<FavoriteStock>> getFavoriteStocks(@RequestParam Long userId) {
		List<FavoriteStock> favoriteStocks = myPageService.getFavoriteStocks(userId);
		return ResponseEntity.ok(favoriteStocks);
	}

	// 📌 관심 종목 삭제
	@DeleteMapping("/favorite-stocks/{stockCode}")
	public ResponseEntity<Void> deleteFavoriteStock(@RequestParam Long userId, @PathVariable String stockCode) {
		myPageService.deleteFavoriteStock(userId, stockCode);
		return ResponseEntity.noContent().build();
	}

	// 📌 사용자가 작성한 댓글 조회
	@GetMapping("/comments")
	public ResponseEntity<List<Community>> getUserComments(@RequestParam Long userId) {
		List<Community> comments = myPageService.getUserComments(userId);
		return ResponseEntity.ok(comments);
	}

	// 📌 사용자가 작성한 댓글 삭제
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> deleteUserComment(@PathVariable Long commentId) {
		myPageService.deleteUserComment(commentId);
		return ResponseEntity.noContent().build();
	}

	// 📌 내 정보 조회
	@GetMapping("/user")
	public ResponseEntity<User> getUserInfo(@RequestParam Long userId) {
		User user = myPageService.getUserInfo(userId);
		return ResponseEntity.ok(user);
	}

	// 📌 회원 정보 수정 (닉네임, 프로필 이미지 변경)
	@PatchMapping("/user-info")
	public ResponseEntity<User> updateUserInfo(@RequestParam Long userId,
		@RequestParam String nickname,
		@RequestParam String profileImageUrl) {
		User updatedUser = myPageService.updateUserInfo(userId, nickname, profileImageUrl);
		return ResponseEntity.ok(updatedUser);
	}

	// 📌 회원 탈퇴
	@DeleteMapping("/user")
	public ResponseEntity<Void> deleteUser(@RequestParam Long userId) {
		myPageService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}
}
