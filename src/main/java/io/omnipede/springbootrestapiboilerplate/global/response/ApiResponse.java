package io.omnipede.springbootrestapiboilerplate.global.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * API response 에 사용할 클래스.
 */
@ApiModel(value="Api response", description="Simple api response")
public class ApiResponse {
	@ApiModelProperty(value="Http 응답 코드", allowEmptyValue=false)
	int status;

	public ApiResponse() { }
	
	public ApiResponse(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
