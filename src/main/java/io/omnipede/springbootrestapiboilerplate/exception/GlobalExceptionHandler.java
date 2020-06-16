package io.omnipede.springbootrestapiboilerplate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.omnipede.springbootrestapiboilerplate.response.ErrorResponse;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * API 상에서 발생하는 모든 exception을 처리하는 클래스.
 * @see "https://cheese10yun.github.io/spring-guide-exception/#null"
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	// Not found url handler
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(final NoHandlerFoundException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		return respondWithError(errorCode);
	}

	// 지원하지 않는 http method 사용시
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		return respondWithError(errorCode);
	}

	// 적절하지 않은 요청 - type mismatch
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		return respondWithError(errorCode);
	}

	// 적절하지 않은 요청 - body 가 없을 때
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		return respondWithError(errorCode);
	}

	// 적절하지 않은 요청 - Request body mismatch
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException (final MethodArgumentNotValidException e) {
		ErrorCode errorCode  = ErrorCode.BAD_REQUEST;
		return respondWithError(errorCode);
	}

	// 비즈니스 로직 상에서 에러 발생 시.
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		return respondWithError(errorCode);
	}
	
	// 그 외의 exception 발생시.
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		e.printStackTrace(System.out);
		return respondWithError(errorCode);
	}

	// Helper method
	private ResponseEntity<ErrorResponse> respondWithError(ErrorCode errorCode) {
		ErrorResponse response = ErrorResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
