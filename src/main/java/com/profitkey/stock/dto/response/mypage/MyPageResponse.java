package com.profitkey.stock.dto.response.mypage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageResponse {

	// 내 정보 응답
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserInfoResponse {
		private Long id;
		private String email;
		private String nickname;
		private String profileImageUrl;
		private Boolean isDeleted;
		private String createdAt;

		public static UserInfoResponse from(UserInfo userInfo) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			return UserInfoResponse.builder()
				.id(userInfo.getAuth() != null ? userInfo.getAuth().getId() : null)  // ✅ Auth에서 ID 가져오기
				.email(userInfo.getAuth() != null ? userInfo.getAuth().getEmail() : null)  // ✅ Auth에서 이메일 가져오기
				.nickname(userInfo.getNickname())  // ✅ UserInfo에서 닉네임 가져오기
				.profileImageUrl(userInfo.getProfileImage())  // ✅ UserInfo에서 프로필 이미지 가져오기
				.isDeleted(
					userInfo.getIsDeleted() != null ? userInfo.getIsDeleted() : false)  // ✅ UserInfo에서 삭제 여부 가져오기
				.createdAt(userInfo.getCreatedAt() != null ? userInfo.getCreatedAt().format(formatter) :
					null)  // ✅ UserInfo에서 생성일 가져오기
				.build();
		}

	}

	// 관심 종목 응답
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class FavoriteStockInfo {
		private String stockCode;
		private String stockName;
		private String marketCategory;

		public static FavoriteStockInfo from(FavoriteStock favoriteStock) {
			return FavoriteStockInfo.builder()
				.stockCode(favoriteStock.getStockCode().getStockCode())
				.stockName(favoriteStock.getStockCode().getStockName())
				.marketCategory(favoriteStock.getStockCode().getMarketCategory())
				.build();
		}

		public static List<FavoriteStockInfo> from(List<FavoriteStock> favoriteStocks) {
			return favoriteStocks.stream()
				.map(FavoriteStockInfo::from)
				.collect(Collectors.toList());
		}
	}

	// 댓글 응답
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CommentInfo {
		private String commentId;
		private String content;
		private String createdAt;

		public static CommentInfo from(Community community) {
			return CommentInfo.builder()
				.commentId(community.getId())
				.content(community.getContent())
				.createdAt(community.getCreatedAt().toString())
				.build();
		}

		public static List<CommentInfo> from(List<Community> communities) {
			return communities.stream()
				.map(CommentInfo::from)
				.collect(Collectors.toList());
		}
	}

	// 전체 응답
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MyPage {
		private UserInfoResponse userInfo;
		private List<FavoriteStockInfo> favoriteStocks;
		private List<CommentInfo> comments;

		public static MyPage from(UserInfo userInfo, List<FavoriteStock> favoriteStocks, List<Community> comments) {
			return MyPage.builder()
				.userInfo(UserInfoResponse.from(userInfo))  // ✅ UserInfo를 기반으로 UserInfoResponse 생성
				.favoriteStocks(FavoriteStockInfo.from(favoriteStocks))
				.comments(CommentInfo.from(comments))
				.build();
		}
	}
}
