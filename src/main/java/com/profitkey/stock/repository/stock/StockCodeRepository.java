package com.profitkey.stock.repository.stock;

import com.profitkey.stock.entity.StockCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockCodeRepository extends JpaRepository<StockCode, String> {
	StockCode findByStockCode(String stockCode);

	List<StockCode> findByStockCodeLike(String code);

}
