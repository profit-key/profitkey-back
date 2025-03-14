package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockInfo;
import com.profitkey.stock.entity.StockSort;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
	List<StockInfo> findByBaseDateAndDivision(String baseDate, StockSort division);

	@Query("SELECT s FROM StockInfo s WHERE s.division = 'BASIC' AND s.stockCode.stockCode = :stockCode ORDER BY s.baseDate DESC")
	Optional<StockInfo> findLatestStockInfo(@Param("stockCode") String stockCode);
}
