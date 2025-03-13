package com.profitkey.stock.repository.mypage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	// Auth 객체로 UserInfo 조회
	Optional<UserInfo> findByAuth(Auth auth);

	// 이메일로 UserInfo 조회
	Optional<UserInfo> findByAuth_Email(String email);

	// deletedAt이 null이 아닌 UserInfo 조회 (탈퇴한 사용자 확인)
	Optional<UserInfo> findByAuth_EmailAndDeletedAtNotNull(String email);
}
