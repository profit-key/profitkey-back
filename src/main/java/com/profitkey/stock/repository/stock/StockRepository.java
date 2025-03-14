package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<StockInfo, Long> {
	@Modifying
	@Transactional
	@Query("DELETE FROM StockInfo s WHERE s.baseDate = :baseDate")
	void deleteByBaseDate(@Param("baseDate") String baseDate);

}
