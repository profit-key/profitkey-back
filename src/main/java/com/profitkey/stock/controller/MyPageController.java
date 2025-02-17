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

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.mypage.UserUpdateRequest;
import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.dto.response.mypage.UserInfoResponse;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.service.MyPageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	/*
	 * 내 정보
	 */
	@GetMapping("/user/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_USER_INFO, description = SwaggerDocs.DESCRIPTION_USER_INFO)
	public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long userId) {
		UserInfoResponse response = myPageService.getUserInfo(userId);

		if (response != null) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PatchMapping("/user/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_UPDATE_USER_INFO, description = SwaggerDocs.DESCRIPTION_UPDATE_USER_INFO)
	public ResponseEntity<UserInfoResponse> updateUserInfo(@PathVariable Long userId,
		@RequestBody UserUpdateRequest request) {
		UserInfoResponse response = myPageService.updateUserInfo(userId, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/user/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_DELETE_USER, description = SwaggerDocs.DESCRIPTION_DELETE_USER)
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		myPageService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	/*
	 * 댓글 (community)
	 */
	@GetMapping("/comments/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_USER_COMMENTS, description = SwaggerDocs.DESCRIPTION_USER_COMMENTS)
	public ResponseEntity<List<MyPageCommunityResponse>> getUserComments(@PathVariable Long userId) {
		List<MyPageCommunityResponse> comments = myPageService.getUserComments(userId);
		return ResponseEntity.ok(comments);
	}

	@DeleteMapping("/comments/{commentId}")
	@Operation(summary = SwaggerDocs.SUMMARY_DELETE_USER_COMMENT, description = SwaggerDocs.DESCRIPTION_DELETE_USER_COMMENT)
	public ResponseEntity<Void> deleteUserComment(@PathVariable Long commentId) {
		myPageService.deleteUserComment(commentId);
		return ResponseEntity.noContent().build();
	}

	/*
	 * 관심 종목
	 */
	@GetMapping("/favorite-stocks/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_FAVORITE_STOCKS, description = SwaggerDocs.DESCRIPTION_FAVORITE_STOCKS)
	public ResponseEntity<List<FavoriteStock>> getFavoriteStocks(@PathVariable Long userId) {
		List<FavoriteStock> favoriteStocks = myPageService.getFavoriteStocks(userId);
		return ResponseEntity.ok(favoriteStocks);
	}

	@DeleteMapping("/favorite-stocks/{userId}/{stockCode}")
	@Operation(summary = SwaggerDocs.SUMMARY_DELETE_FAVORITE_STOCK, description = SwaggerDocs.DESCRIPTION_DELETE_FAVORITE_STOCK)
	public ResponseEntity<Void> deleteFavoriteStock(@PathVariable Long userId, @PathVariable String stockCode) {
		myPageService.deleteFavoriteStock(userId, stockCode);
		return ResponseEntity.noContent().build();
	}
}
