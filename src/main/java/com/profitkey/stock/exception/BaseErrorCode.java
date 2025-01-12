package com.profitkey.stock.exception;

import com.profitkey.stock.dto.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();
    String getExplainError() throws NoSuchFieldException;
}

