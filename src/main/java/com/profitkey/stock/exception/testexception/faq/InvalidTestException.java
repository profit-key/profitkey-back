package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.faqcategory.FaqCategoryCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class InvalidTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new InvalidTestException();

	private InvalidTestException() {
		super(FaqCategoryCode.INVALID_IS_USE);
	}
}
