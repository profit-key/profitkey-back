package com.profitkey.stock.exception.testexception.board;

import com.profitkey.stock.exception.errorcode.board.BoardErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class TestException extends ProfitCodeException {
    public static final ProfitCodeException EXCEPTION = new TestException();

    private TestException() {
        super(BoardErrorCode.BOARD_NOT_FOUND);
    }
}
