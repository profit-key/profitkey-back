package com.profitkey.stock.exception.errorcode;

import com.profitkey.stock.dto.common.ErrorReason;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfitCodeException extends RuntimeException {
    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}
