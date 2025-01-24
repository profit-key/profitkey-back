package com.profitkey.stock.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteStockId implements Serializable {
	private Long user;
	private Long stockCode;
} 