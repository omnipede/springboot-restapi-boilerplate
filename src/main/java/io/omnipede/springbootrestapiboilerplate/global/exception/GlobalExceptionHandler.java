package io.omnipede.springbootrestapiboilerplate.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.omnipede.springbootrestapiboilerplate.global.response.ErrorApiResponse;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * API 상에서 발생하는 모든 exception을 처리하는 클래스.
 * @see "https://cheese10yun.github.io/spring-guide-exception/#null"
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * API end point가 존재하지 않는 경우
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorApiResponse> handleNoHandlerFoundException(final NoHandlerFoundException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		return respondWithError(errorCode);
	}

	/**
	 * 지원하지 않는 http method 요청시 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorApiResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
		ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
		return respondWithError(errorCode);
	}

	/**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorApiResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		return respondWithError(errorCode);
	}

	/**
	 *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException (final MethodArgumentNotValidException e) {
		ErrorCode errorCode  = ErrorCode.BAD_REQUEST;
		return respondWithError(errorCode);
	}

	/**
	 * 비지니스 로직 상에서 에러가 발생할 경우
	 */
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorApiResponse> handleBusinessException(final BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		return respondWithError(errorCode);
	}

	/**
	 * 그 외 서버 에러 발생 시
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorApiResponse> handleException(final Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		e.printStackTrace(System.out);
		return respondWithError(errorCode);
	}

	// Helper method
	private ResponseEntity<ErrorApiResponse> respondWithError(ErrorCode errorCode) {
		ErrorApiResponse response = ErrorApiResponse.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
}
