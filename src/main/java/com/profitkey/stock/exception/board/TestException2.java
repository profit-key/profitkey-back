package com.profitkey.stock.exception.board;

import com.profitkey.stock.dto.BoardErrorCode;
import com.profitkey.stock.exception.ProfitCodeException;

public class TestException2 extends ProfitCodeException {
    public static final ProfitCodeException EXCEPTION = new TestException2();

    private TestException2() {
        super(BoardErrorCode.BOARD_INVALID_OPTION);
    }
}
