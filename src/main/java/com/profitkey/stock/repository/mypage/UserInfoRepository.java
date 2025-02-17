package com.profitkey.stock.repository.mypage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profitkey.stock.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findById(Long id);

	// Auth 테이블의 이메일을 기준으로 UserInfo 조회
	Optional<UserInfo> findByAuth_Email(String email);

	Optional<UserInfo> findByAuth_EmailAndIsDeleted(String email, boolean isDeleted);

}
