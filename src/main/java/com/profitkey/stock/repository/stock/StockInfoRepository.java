package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockInfo;
import com.profitkey.stock.entity.StockSort;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
	List<StockInfo> findByBaseDateAndDivision(String baseDate, StockSort division);

	@Query("SELECT s FROM StockInfo s WHERE s.division = 'BASIC' ORDER BY s.baseDate DESC LIMIT 1")
	Optional<StockInfo> findLatestStockInfo();
}
