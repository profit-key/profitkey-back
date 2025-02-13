package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.GlobalErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class NotFoundTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new NotFoundTestException();

	private NotFoundTestException() {
		super(GlobalErrorCode.NOTFOUND_ID);
	}
}
