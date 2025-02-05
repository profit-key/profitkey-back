package com.profitkey.stock.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String email;
	@Column(name = "PROFILE_IMAGE_URL", length = 500)
	private String profileImageUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "PROVIDER")
	private AuthProvider provider;

	@Column(name = "ACCESS_TOKEN", columnDefinition = "TEXT")
	private String accessToken;

	@Column(length = 50)
	private String nickname;

	@Column(name = "IS_DELETED", nullable = false)
	private Boolean isDeleted = false;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = true)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<FavoriteStock> favoriteStocks;

	@Builder
	private User(String email, AuthProvider provider, String accessToken, String nickname, String profileImageUrl) {
		this.email = email;
		this.provider = provider;
		this.accessToken = accessToken;
		this.nickname = nickname;
		this.isDeleted = false;
		this.profileImageUrl = profileImageUrl;
	}
} 