package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.GlobalErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class UnAuthorizedException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new UnAuthorizedException();

	public UnAuthorizedException() {
		super(GlobalErrorCode.UNAUTHORIZED_USER);
	}
}
