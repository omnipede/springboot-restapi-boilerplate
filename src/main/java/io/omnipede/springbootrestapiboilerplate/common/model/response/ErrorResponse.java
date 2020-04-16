package io.omnipede.springbootrestapiboilerplate.common.model.response;

import io.omnipede.springbootrestapiboilerplate.common.model.ErrorCode;

/**
 * API 에러 response 에 사용할 클래스.
 */
public class ErrorResponse extends Response {
	private String code;
	private String message;

	public ErrorResponse() {
		
	}
	
	public ErrorResponse(int status, String code, String message) {
		super(status);
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 에러 코드 객체를 에러 response 객체로 바꿔주는 메소드.
	 * @param errorCode
	 */
	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
	}
}
