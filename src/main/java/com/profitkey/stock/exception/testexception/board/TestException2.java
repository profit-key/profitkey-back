package com.profitkey.stock.exception.testexception.board;

import com.profitkey.stock.exception.errorcode.board.BoardErrorCode;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

public class TestException2 extends ProfitCodeException {
    public static final ProfitCodeException EXCEPTION = new TestException2();

    private TestException2() {
        super(BoardErrorCode.BOARD_INVALID_OPTION);
    }
}
