package io.omnipede.springbootrestapiboilerplate.global.response;

import io.omnipede.springbootrestapiboilerplate.global.exception.ErrorCode;

/**
 * API 에러 response 에 사용할 클래스.
 */
public class ErrorApiResponse extends ApiResponse {
	private String code;
	private String message;

	public ErrorApiResponse() {
		
	}
	
	public ErrorApiResponse(int status, String code, String message) {
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
	public static ErrorApiResponse of(ErrorCode errorCode) {
		return new ErrorApiResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
	}
}
