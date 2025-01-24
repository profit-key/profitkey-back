package com.profitkey.stock.exception.docs.faqcategory;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.SwaggerException;
import com.profitkey.stock.exception.docs.ExceptionDocs;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;
import com.profitkey.stock.exception.testexception.faqcategory.DuplicateTestException;
import com.profitkey.stock.exception.testexception.faqcategory.InvalidTestException;
import com.profitkey.stock.exception.testexception.faqcategory.NotFoundTestException;

@ExceptionDocs
public class FaqCategoryExceptionDocs implements SwaggerException {

	@ExplainError("고유값 중복")
	public ProfitCodeException DUPLICATE_CATEGORY_NAME = DuplicateTestException.EXCEPTION;

	@ExplainError("isUse 타입 오류")
	public ProfitCodeException INVALID_IS_USE = InvalidTestException.EXCEPTION;
}
