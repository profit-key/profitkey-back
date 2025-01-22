package com.profitkey.stock.exception.docs.stock;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.SwaggerException;
import com.profitkey.stock.exception.docs.ExceptionDocs;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;
import com.profitkey.stock.exception.testexception.stock.StockException;

@ExceptionDocs
public class StockExceptionDocs implements SwaggerException {
	@ExplainError("테스트")
	public ProfitCodeException 테스트_하는중 = StockException.EXCEPTION;

	// ...
}
