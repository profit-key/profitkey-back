package com.profitkey.stock.exception.handler;

import com.profitkey.stock.dto.common.ErrorResponse;
import com.profitkey.stock.exception.errorcode.ProfitCodeException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * 전역 예외 처리 핸들러
 * 애플리케이션에서 발생하는 모든 예외를 일관된 방식으로 처리합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 커스텀 예외 처리
	 * 비즈니스 로직에서 직접 발생시킨 예외를 처리합니다.
	 * 예시: throw new ProfitCodeException(ErrorCode.NOT_FOUND_USER);
	 */
	@ExceptionHandler(ProfitCodeException.class)
	public ResponseEntity<ErrorResponse> handleProfitCodeException(ProfitCodeException e, HttpServletRequest request) {
		log.error("ProfitCodeException: {}", e.getMessage());
		return ResponseEntity.status(e.getErrorReason().getStatus())
			.body(new ErrorResponse(e.getErrorReason(), request.getRequestURI()));
	}

	/**
	 * 데이터베이스 무결성 제약 조건 위반 처리
	 * - Unique 제약조건 위반 (중복된 값 입력)
	 * - NOT NULL 필드에 NULL 값 입력
	 * - 외래키 제약조건 위반 등을 처리합니다.
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e,
		HttpServletRequest request) {
		log.error("Data integrity violation: {}", e.getMessage());

		if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
			String errorMessage = e.getMessage();
			String duplicateValue = "";

			int startIndex = errorMessage.indexOf("'") + 1;
			int endIndex = errorMessage.indexOf("'", startIndex);
			if (startIndex > 0 && endIndex > startIndex) {
				duplicateValue = errorMessage.substring(startIndex, endIndex);
			}

			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(HttpStatus.CONFLICT.value(), "DUPLICATE_ENTRY",
					String.format("이미 존재하는 데이터입니다. (중복값: %s)", duplicateValue), request.getRequestURI()));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "DATA_INTEGRITY_VIOLATION", "데이터 무결성 위반이 발생했습니다.",
				request.getRequestURI()));
	}

	/**
	 * JSON 요청 데이터 파싱 실패 처리
	 * - Boolean 필드에 "true"가 아닌 "dsa" 입력
	 * - 날짜 형식이 잘못된 경우
	 * - JSON 형식이 올바르지 않은 경우 등을 처리합니다.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
		HttpServletRequest request) {
		log.error("Message not readable: {}", e.getMessage());

		String message = "잘못된 형식의 요청 데이터입니다.";

		if (e.getMessage() != null) {
			if (e.getMessage().contains("Unrecognized token")) {
				String errorMessage = e.getMessage();
				int tokenIndex = errorMessage.indexOf("Unrecognized token '") + 19;
				int endIndex = errorMessage.indexOf("'", tokenIndex);
				if (tokenIndex > 18 && endIndex > tokenIndex) {
					String invalidValue = errorMessage.substring(tokenIndex, endIndex);
					message = String.format("잘못된 형식의 값이 입력되었습니다. (입력값: %s)", invalidValue);
				}
			} else if (e.getMessage().contains("Cannot deserialize value of type")) {
				String errorMessage = e.getMessage();
				int typeIndex = errorMessage.indexOf("of type `") + 9;
				int endIndex = errorMessage.indexOf("`", typeIndex);
				if (typeIndex > 8 && endIndex > typeIndex) {
					String expectedType = errorMessage.substring(typeIndex, endIndex);
					message = String.format("잘못된 데이터 타입입니다. (요구되는 타입: %s)", expectedType);
				}
			}
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "INVALID_REQUEST_BODY", message,
				request.getRequestURI()));
	}

	/**
	 * @Valid 어노테이션을 통한 유효성 검증 실패 처리
	 * - @NotNull이 붙은 필드에 null 입력
	 * - @Size(min = 2)가 붙은 필드에 한 글자만 입력
	 * - @Email이 붙은 필드에 잘못된 이메일 형식 입력 등을 처리합니다.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e,
		HttpServletRequest request) {
		log.error("Validation error: {}", e.getMessage());
		String errorMessage = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.findFirst()
			.orElse("유효성 검증에 실패했습니다.");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR", errorMessage,
				request.getRequestURI()));
	}

	/**
	 * 요청 파라미터 바인딩 실패 처리
	 * - GET 요청의 쿼리 파라미터 타입 불일치
	 * - Form 데이터 바인딩 실패 등을 처리합니다.
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleBindException(BindException e, HttpServletRequest request) {
		log.error("Bind error: {}", e.getMessage());
		String errorMessage = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.findFirst()
			.orElse("요청 데이터 바인딩에 실패했습니다.");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(
				new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BIND_ERROR", errorMessage, request.getRequestURI()));
	}

	/**
	 * ResponseStatusException 처리
	 * 400, 404 등 HTTP 상태 코드를 직접 지정한 예외를 처리합니다.
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e,
		HttpServletRequest request) {
		log.error("Status error occurred: {}", e.getMessage());
		return ResponseEntity.status(e.getStatusCode())
			.body(new ErrorResponse(e.getStatusCode().value(), e.getStatusCode().toString(), e.getReason(),
				request.getRequestURI()));
	}

	/**
	 * 그 외 모든 예외를 처리하는 핸들러
	 * - NullPointerException
	 * - IllegalArgumentException
	 * - 기타 예상치 못한 모든 예외를 처리합니다.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, HttpServletRequest request) {
		log.error("Unexpected error occurred: ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR",
				"서버 내부 오류가 발생했습니다.", request.getRequestURI()));
	}
}