package com.profitkey.stock.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.FavoriteStock;
import com.profitkey.stock.entity.User;

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
	public static class UserInfo {
		private Long id;
		private String email;
		private String nickname;
		private String profileImageUrl;
		private Boolean isDeleted;
		private String createdAt;

		public static UserInfo from(User user) {
			return UserInfo.builder()
				.id(user.getId())
				.email(user.getEmail())
				.nickname(user.getNickname())
				.profileImageUrl(user.getProfileImageUrl())
				.isDeleted(user.getIsDeleted())
				.createdAt(user.getCreatedAt().toString())
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
		private UserInfo userInfo;
		private List<FavoriteStockInfo> favoriteStocks;
		private List<CommentInfo> comments;

		public static MyPage from(User user, List<FavoriteStock> favoriteStocks, List<Community> comments) {
			return MyPage.builder()
				.userInfo(UserInfo.from(user))
				.favoriteStocks(FavoriteStockInfo.from(favoriteStocks))
				.comments(CommentInfo.from(comments))
				.build();
		}
	}
}
