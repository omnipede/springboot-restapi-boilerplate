package io.omnipede.springbootrestapiboilerplate.global.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Success (200) response 에 사용하는 클래스.
 * {
 *   "status": 200,
 *   "data": T
 * }
 */
@ApiModel(value="Response with data", description="데이터를 포함한 응답")
public class ApiResponseWithData<T> extends ApiResponse {
	@ApiModelProperty(value="응답 데이터")
	private T data;
	
	public ApiResponseWithData() {
		// Success status code 는 200이므로 생성자 매개변수로 200을 넘긴다.
		super(200);
	}
	
	public ApiResponseWithData(T data) {
		// Success status code 는 200이므로 생성자 매개변수로 200을 넘긴다.
		super(200);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
}
