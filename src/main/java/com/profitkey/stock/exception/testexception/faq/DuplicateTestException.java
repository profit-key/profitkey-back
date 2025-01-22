package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.faqcategory.FaqCategoryCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class DuplicateTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new DuplicateTestException();

	private DuplicateTestException() {
		super(FaqCategoryCode.DUPLICATE_CATEGORY_NAME);
	}

}
