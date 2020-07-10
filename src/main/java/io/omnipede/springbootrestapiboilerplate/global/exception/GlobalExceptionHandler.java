package io.omnipede.springbootrestapiboilerplate.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * API 상에서 발생하는 모든 exception 을 처리하는 클래스.
 * @see "https://cheese10yun.github.io/spring-guide-exception/"
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorJsonResponse> handleMethodArgumentNotValidException (final MethodArgumentNotValidException e) {
		ErrorCode errorCode  = ErrorCode.BAD_REQUEST;
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode, e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * enum type 일치하지 않아 binding 못할 경우 발생
	 * 주로 @RequestParam enum 으로 binding 못했을 경우 발생 (Query parameter 등)
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorJsonResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode, e);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * Invalid 한 JSON 을 body 로 전달한 경우
	 * {
	 *     "name": "foo bar",  <-- 마지막에 ',' 로 끝남
	 * }
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorJsonResponse> handleHttpMessageNotReadableException (final HttpMessageNotReadableException e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * API end point 가 존재하지 않는 경우
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorJsonResponse> handleNoHandlerFoundException(final NoHandlerFoundException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * 지원하지 않는 http method 요청시 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorJsonResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * 비지니스 로직 상에서 에러가 발생할 경우
	 */
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorJsonResponse> handleBusinessException(final BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	/**
	 * 그 외 서버 에러 발생 시
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorJsonResponse> handleException(final Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		e.printStackTrace(System.out);
		final ErrorJsonResponse response = ErrorJsonResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
