package com.profitkey.stock.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.dto.request.mypage.FavoriteStockRequest;
import com.profitkey.stock.dto.response.mypage.FavoriteStockResponse;
import com.profitkey.stock.dto.response.mypage.MyPageCommunityResponse;
import com.profitkey.stock.dto.response.mypage.UserInfoResponse;
import com.profitkey.stock.repository.mypage.FavoriteStockRepository;
import com.profitkey.stock.service.AuthService;
import com.profitkey.stock.service.MyPageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;
	private final AuthService authService;
	private final FavoriteStockRepository favoriteStockRepository;

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

	//닉네임 수정
	@PutMapping("/{userId}/nickname")
	@Operation(summary = SwaggerDocs.SUMMARY_UPDATE_NICKNAME, description = SwaggerDocs.DESCRIPTION_UPDATE_NICKNAME)
	public UserInfoResponse updateNickname(
		@PathVariable Long userId,
		@RequestParam String nickname
	) {
		return myPageService.updateNickname(userId, nickname);
	}

	//프로필 이미지 수정
	@PutMapping(value = "/{userId}/profile-image", consumes = "multipart/form-data", produces = "application/json")
	@Operation(
		summary = SwaggerDocs.SUMMARY_UPDATE_PROFILE_IMAGE,
		description = SwaggerDocs.DESCRIPTION_UPDATE_PROFILE_IMAGE,
		responses = {
			@ApiResponse(responseCode = "200", description = "성공적으로 프로필 사진이 수정되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class))),
			@ApiResponse(responseCode = "400", description = "파일 업로드 실패", content = @Content),
			@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content)
		}
	)
	public UserInfoResponse updateProfileImage(
		@PathVariable Long userId,
		@RequestPart("profileImage") MultipartFile profileImage
	) throws IOException {
		return myPageService.updateProfileImage(userId, profileImage);
	}

	//프로필 이미지 삭제
	@DeleteMapping("/{userId}/profile-image")
	@Operation(
		summary = SwaggerDocs.SUMMARY_DELETE_PROFILE_IMAGE,
		description = SwaggerDocs.DESCRIPTION_DELETE_PROFILE_IMAGE,
		responses = {
			@ApiResponse(responseCode = "200", description = "프로필 사진이 성공적으로 삭제되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class))),
			@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content)
		}
	)
	public UserInfoResponse deleteProfileImage(
		@PathVariable Long userId
	) {
		return myPageService.deleteProfileImage(userId);
	}

	// /me 엔드포인트 추가
	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_ME, description = SwaggerDocs.DESCRIPTION_TOKEN_ME)
	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> getUserInfo(HttpServletRequest request) {
		Map<String, Object> userInfo = authService.getUserInfoFromToken(request);
		return ResponseEntity.ok(userInfo);
	}

	//회원 탈퇴
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

	/*
	 * 관심 종목
	 */
	//관심 종목 추가
	@PostMapping("{userId}/favorite-stocks")
	@Operation(summary = SwaggerDocs.SUMMARY_POST_FAVORITE_STOCKS, description = SwaggerDocs.DESCRIPTION_POST_FAVORITE_STOCKS)
	public void addFavoriteStock(@RequestBody FavoriteStockRequest request) {
		myPageService.addFavoriteStock(request.getUserId(), request.getStockCode());
	}

	//관심 종목 조회
	@GetMapping("/favorite-stocks/{userId}")
	@Operation(summary = SwaggerDocs.SUMMARY_FAVORITE_STOCKS, description = SwaggerDocs.DESCRIPTION_FAVORITE_STOCKS)
	public ResponseEntity<List<FavoriteStockResponse>> getFavoriteStocks(@PathVariable Long userId) {
		List<FavoriteStockResponse> response = myPageService.getFavoriteStocks(userId);
		return ResponseEntity.ok(response);
	}

	//관심 종목 삭제
	@DeleteMapping("/favorite-stocks/{userId}/{stockCode}")
	@Operation(summary = SwaggerDocs.SUMMARY_DELETE_FAVORITE_STOCK, description = SwaggerDocs.DESCRIPTION_DELETE_FAVORITE_STOCK)
	public ResponseEntity<Void> deleteFavoriteStock(@PathVariable Long userId, @PathVariable String stockCode) {
		myPageService.deleteFavoriteStock(userId, stockCode);
		return ResponseEntity.noContent().build();
	}

}
