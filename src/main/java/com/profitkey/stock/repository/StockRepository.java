package com.profitkey.stock.repository;

import com.profitkey.stock.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Board, Long> {
}
