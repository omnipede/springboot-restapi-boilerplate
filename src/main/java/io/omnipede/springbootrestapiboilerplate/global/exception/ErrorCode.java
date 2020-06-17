package io.omnipede.springbootrestapiboilerplate.global.exception;

/**
 * 모든 비즈니스 로직 에러 코드를 관리하는 클래스.
 */
public enum ErrorCode {

	URL_NOT_FOUND(404, "C404", "Not found url"),
	BAD_REQUEST(403, "C403", "Bad request"),
	RESOURCE_NOT_EXISTS(404, "C001", "Resource not exists"),
	INTERNAL_SERVER_ERROR(500, "C002", "Internal server error occured");

	private final int status;
	private final String code;
	private final String message;
	
	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
