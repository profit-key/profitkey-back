package com.profitkey.stock.exception.board;

import com.profitkey.stock.dto.BoardErrorCode;
import com.profitkey.stock.exception.ProfitCodeException;

public class TestException extends ProfitCodeException {
    public static final ProfitCodeException EXCEPTION = new TestException();

    private TestException() {
        super(BoardErrorCode.BOARD_NOT_FOUND);
    }
}
