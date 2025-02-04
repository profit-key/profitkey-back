package com.profitkey.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);  // 이메일로 유저 찾기

	/** 🔹 특정 사용자의 정보 조회 */
	Optional<User> findById(Long userId);
}
