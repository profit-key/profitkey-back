package com.profitkey.stock.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MyPage")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "nickname", length = 50)
	private String nickname;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "profile_image_url", length = 500)  // 🔥 프로필 이미지 추가
	private String profileImageUrl;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public MyPage(User user, String nickname, String email, String profileImageUrl, Boolean isDeleted,
		LocalDateTime createdAt) {
		this.user = user;
		this.nickname = nickname;
		this.email = email;
		this.profileImageUrl = profileImageUrl;  // 🔥 추가
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
	}
}
