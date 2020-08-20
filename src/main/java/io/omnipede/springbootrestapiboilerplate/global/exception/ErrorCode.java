package io.omnipede.springbootrestapiboilerplate.global.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 모든 비즈니스 로직 에러 코드를 관리하는 클래스.
 */
@Getter
public enum ErrorCode {

	URL_NOT_FOUND(404, "C404", "Not found url"),
	BAD_REQUEST(400, "C400", "Bad request"),
	RESOURCE_NOT_EXISTS(404, "C001", "Resource not exists"),
	INTERNAL_SERVER_ERROR(500, "C002", "Internal server error");

	private final int status;
	private final String code;
	private final String message;
	
	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
