package com.profitkey.stock.repository.mypage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.profitkey.stock.entity.FavoriteStock;

@Repository
public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, Long> {
	/** 🔹 사용자의 관심 종목 조회 */
	List<FavoriteStock> findByUserId(Long userId);

	/** 🔹 관심 종목 삭제 */
	@Modifying
	@Transactional
	@Query("DELETE FROM FavoriteStock f WHERE f.user.id = :userId AND f.stockCode = :stockCode")
	void deleteByUserIdAndStockCode(@Param("userId") Long userId, @Param("stockCode") String stockCode);
}
