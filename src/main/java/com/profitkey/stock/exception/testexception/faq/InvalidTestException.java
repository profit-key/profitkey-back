package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.GlobalErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class InvalidTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new InvalidTestException();

	private InvalidTestException() {
		super(GlobalErrorCode.INVALID_IS_USE);
	}
}
