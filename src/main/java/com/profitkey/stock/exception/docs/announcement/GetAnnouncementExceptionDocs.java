package com.profitkey.stock.exception.docs.announcement;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.SwaggerException;
import com.profitkey.stock.exception.docs.ExceptionDocs;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;
import com.profitkey.stock.exception.testexception.faq.NotFoundTestException;

@ExceptionDocs

public class GetAnnouncementExceptionDocs implements SwaggerException {
	@ExplainError("ID 조회 에러")
	public ProfitCodeException NOTFOUND_ID = NotFoundTestException.EXCEPTION;
}
