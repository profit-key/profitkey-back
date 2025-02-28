package com.profitkey.stock.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.StockInfo;

public interface StockRepository extends JpaRepository<StockInfo, Long> {
}
