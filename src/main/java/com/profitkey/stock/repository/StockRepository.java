package com.profitkey.stock.repository;

import com.profitkey.stock.entity.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockInfo, Long> {
}
