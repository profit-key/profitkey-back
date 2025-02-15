package com.profitkey.stock.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "UserInfo")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 PK
	@Column(name = "USER_ID")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(length = 255)
	private String profileImage;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "auth_id", referencedColumnName = "id")
	private Auth auth;  // UserInfo와 1:1 관계인 Auth 객체

	@Column(name = "IS_DELETED", nullable = false)
	private Boolean isDeleted = false;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	@Builder
	public UserInfo(String nickname, String profileImage, Auth auth) {
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.auth = auth;
		this.isDeleted = false;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
