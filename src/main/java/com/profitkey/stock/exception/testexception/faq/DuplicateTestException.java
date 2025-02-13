package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.GlobalErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class DuplicateTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new DuplicateTestException();

	private DuplicateTestException() {
		super(GlobalErrorCode.DUPLICATE_CATEGORY_NAME);
	}

}
