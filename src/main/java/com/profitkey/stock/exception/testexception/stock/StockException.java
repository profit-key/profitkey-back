package com.profitkey.stock.exception.testexception.stock;

import com.profitkey.stock.exception.errorcode.stock.StockErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class StockException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new StockException();

	private StockException() {
		super(StockErrorCode.STOCK_INVALID_OPTION);
	}
}
