package io.omnipede.rest.global.exception;

import lombok.Getter;

/**
 * 모든 비즈니스 로직 에러 코드를 관리하는 클래스.
 */
@Getter
public enum ErrorCode {

	URL_NOT_FOUND(404, 40400, "Not found url"),
	BAD_REQUEST(400, 40000, "Bad request"),
	RESOURCE_NOT_EXISTS(404, 40401, "Resource not exists"),
	INTERNAL_SERVER_ERROR(500, 50000, "Internal server error");

	private final int status;
	private final int code;
	private final String message;
	
	ErrorCode(final int status, final int code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
