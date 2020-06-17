package io.omnipede.springbootrestapiboilerplate.global.response;

import io.omnipede.springbootrestapiboilerplate.global.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * API 에러 response 에 사용할 클래스.
 */
@ApiModel(value="Api error response", description="에러 발생 응답")
public class ErrorApiResponse extends ApiResponse {
	@ApiModelProperty(value="에러코드", allowEmptyValue=false)
	private String code;
	@ApiModelProperty(value="에러 메시지", allowEmptyValue=false)
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
