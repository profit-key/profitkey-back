package com.profitkey.stock.docs;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.ProfitCodeException;
import com.profitkey.stock.exception.SwaggerException;
import com.profitkey.stock.exception.board.TestException;
import com.profitkey.stock.exception.board.TestException2;

@ExceptionDocs
public class BoardExceptionDocs implements SwaggerException {

    @ExplainError("테스트")
    public ProfitCodeException 테스트_하는중 = TestException.EXCEPTION;
    @ExplainError("테스트2")
    public ProfitCodeException 테스트_하는중2 = TestException2.EXCEPTION;
}
