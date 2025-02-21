package com.profitkey.stock.repository;

import com.profitkey.stock.entity.StockCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockCodeRepository extends JpaRepository<StockCode, String> {

}
