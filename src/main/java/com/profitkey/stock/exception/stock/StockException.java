package com.profitkey.stock.exception.stock;

import com.profitkey.stock.dto.StockErrorCode;
import com.profitkey.stock.exception.ProfitCodeException;

public class StockException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new StockException();

	private StockException() {
		super(StockErrorCode.STOCK_INVALID_OPTION);
	}
}
