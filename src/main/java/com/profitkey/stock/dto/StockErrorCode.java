package com.profitkey.stock.dto;

import static com.profitkey.stock.consts.ProfitStatic.*;
import com.profitkey.stock.annotation.ExplainError;
import com.profitkey.stock.exception.BaseErrorCode;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockErrorCode implements BaseErrorCode {
	@ExplainError("알수없는 서버오류")
	STOCK_INVALID_OPTION(INTERNAL_SERVER, "500", "개발자가 코드를 잘못짠거아닐까요?");

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