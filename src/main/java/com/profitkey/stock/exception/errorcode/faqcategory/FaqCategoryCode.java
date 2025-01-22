package com.profitkey.stock.exception.errorcode.faqcategory;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.dto.common.ErrorReason;
import com.profitkey.stock.exception.errorcode.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum FaqCategoryCode implements BaseErrorCode{
    @ExplainError("고유값 중복")
    DUPLICATE_CATEGORY_NAME(409, "DUPLICATE_ENTRY", "이미 존재하는 데이터입니다. (중복값: ${중복 key 이름})"),
    @ExplainError("isUse 오류")
    INVALID_IS_USE(400, "DATA_INTEGRITY_VIOLATION", "잘못된 형식의 값이 입력되었습니다.");

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
