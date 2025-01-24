package com.profitkey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);  // 이메일로 유저 찾기
}
