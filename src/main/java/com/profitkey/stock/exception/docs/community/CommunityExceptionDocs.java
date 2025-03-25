package com.profitkey.stock.exception.docs.community;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.SwaggerException;
import com.profitkey.stock.exception.docs.ExceptionDocs;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;
import com.profitkey.stock.exception.testexception.faq.NotFoundTestException;
import com.profitkey.stock.exception.testexception.faq.UnAuthorizedException;

@ExceptionDocs
public class CommunityExceptionDocs implements SwaggerException {
	@ExplainError("존재하지 않음")
	public ProfitCodeException NOT_FOUND = NotFoundTestException.EXCEPTION;

	@ExplainError("접근권한 없음")
	public ProfitCodeException UNAUTH_USER = UnAuthorizedException.EXCEPTION;

}
