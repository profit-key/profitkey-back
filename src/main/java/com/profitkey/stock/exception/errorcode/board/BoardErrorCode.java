package com.profitkey.stock.exception.errorcode.board;

import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.dto.common.ErrorReason;
import com.profitkey.stock.exception.errorcode.BaseErrorCode;

import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import static com.profitkey.stock.consts.ProfitStatic.BAD_REQUEST;
import static com.profitkey.stock.consts.ProfitStatic.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum BoardErrorCode implements BaseErrorCode {
    @ExplainError("해당 글을 못찾을때")
    BOARD_NOT_FOUND(NOT_FOUND, "404_1", "게시글을를 찾을 수 없습니다."),
    @ExplainError("게시글 입력항목 오류났을때")
    BOARD_INVALID_OPTION(BAD_REQUEST, "400_1", "입력값을 확인바랍니다.")
    ;
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