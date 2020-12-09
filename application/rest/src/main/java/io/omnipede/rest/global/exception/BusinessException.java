package io.omnipede.rest.global.exception;

/**
 * API 비즈니스 로직 상에서 에러 발생시 throw 하는 exception.
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private ErrorCode errorCode;
	
	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
