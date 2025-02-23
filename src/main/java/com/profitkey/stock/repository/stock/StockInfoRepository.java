package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
	List<StockInfo> findByBaseDate(String baseDate);
}
