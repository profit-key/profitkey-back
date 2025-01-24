package com.profitkey.stock.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider")
	private AuthProvider provider;

	@Column(name = "access_token", columnDefinition = "TEXT")
	private String accessToken;

	@Column(length = 50)
	private String nickname;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user")
	private List<Board> boards;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<FavoriteStock> favoriteStocks;

	@Builder
	private User(String email, AuthProvider provider, String accessToken, String nickname) {
		this.email = email;
		this.provider = provider;
		this.accessToken = accessToken;
		this.nickname = nickname;
		this.isDeleted = false;
	}
} 