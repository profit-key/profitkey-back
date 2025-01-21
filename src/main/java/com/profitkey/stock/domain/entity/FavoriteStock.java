package com.profitkey.stock.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FavoriteStocks")
@IdClass(FavoriteStockId.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FavoriteStock {
    @Id
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "stock_code", columnDefinition = "VARCHAR(12)")
    private StockCode stockCode;
} 