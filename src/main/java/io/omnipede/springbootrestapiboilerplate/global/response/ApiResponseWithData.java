package io.omnipede.springbootrestapiboilerplate.global.response;

/**
 * Success (200) response 에 사용하는 클래스.
 * {
 *   "status": 200,
 *   "data": T
 * }
 */
public class ApiResponseWithData<T> extends ApiResponse {
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
