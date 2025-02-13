package com.profitkey.stock.exception.testexception.faq;

import com.profitkey.stock.exception.errorcode.ProfitCodeException;
import com.profitkey.stock.exception.errorcode.faqcategory.FaqCategoryCode;

public class NotFoundTestException extends ProfitCodeException {
	public static final ProfitCodeException EXCEPTION = new NotFoundTestException();

	private NotFoundTestException() {
		super(FaqCategoryCode.NOTFOUND_CATRGORY_ID);
	}
}
