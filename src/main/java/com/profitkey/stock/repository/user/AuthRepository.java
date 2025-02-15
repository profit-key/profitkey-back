package com.profitkey.stock.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
	Optional<Auth> findByEmail(String email);

	Optional<Auth> findById(Long userId);
}
