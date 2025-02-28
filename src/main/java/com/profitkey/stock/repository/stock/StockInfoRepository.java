package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
	List<StockInfo> findByBaseDate(String baseDate);

	@Query("SELECT s FROM StockInfo s ORDER BY s.baseDate DESC LIMIT 1")
	Optional<StockInfo> findLatestStockInfo();
}
