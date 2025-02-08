package com.profitkey.stock.repository.mypage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profitkey.stock.entity.MyPage;

@Repository
public interface MyPageRepository extends JpaRepository<MyPage, Long> {
	MyPage findByUserId(Long userId);
}
