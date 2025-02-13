package com.profitkey.stock.exception.errorcode;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.dto.common.ErrorReason;

import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
    @ExplainError("고유값 중복")
    DUPLICATE_CATEGORY_NAME(409, "409 CONFLICT", "이미 존재하는 데이터입니다. (중복값: ${중복 key 이름})"),
    @ExplainError("json 입력 오류")
    INVALID_IS_USE(400, "400 BAD_REQUEST", "잘못된 형식의 값이 입력되었습니다."),
    @ExplainError("Notfound 오류")
    NOTFOUND_ID(404,"404 NOT_FOUND", "해당 데이터가 존재하지 않습니다. id: ${key값}"),
    @ExplainError("SERVER_ERROR")
    SERVER_ERROR(500, "GLOBAL_500", "알 수 없는 오류입니다.");
    private Integer status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }
    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}