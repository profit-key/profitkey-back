package com.profitkey.stock.exception.errorcode;

import com.profitkey.stock.dto.common.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();
    String getExplainError() throws NoSuchFieldException;
}

