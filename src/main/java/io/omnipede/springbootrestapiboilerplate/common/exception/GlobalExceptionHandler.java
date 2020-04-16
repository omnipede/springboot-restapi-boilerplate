package io.omnipede.springbootrestapiboilerplate.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.omnipede.springbootrestapiboilerplate.common.model.ErrorCode;
import io.omnipede.springbootrestapiboilerplate.common.model.response.ErrorResponse;

/**
 * API 상에서 발생하는 모든 exception을 처리하는 클래스.
 * @see https://cheese10yun.github.io/spring-guide-exception/#null
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	// 비즈니스 로직 상에서 에러 발생 시.
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse response = ErrorResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
	
	// 그 외의 exception 발생시.
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		ErrorResponse response = ErrorResponse.of(errorCode);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
