package com.profitkey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
