package io.omnipede.springbootrestapiboilerplate.common.model.response;

/**
 * Success (200) response 에 사용하는 클래스.
 * @param T json response data 필드에 들어가는 값.
 * {
 *   "status": 200,
 *   "data": T
 * }
 */
public class OkResponse<T extends Object> extends Response {
	private T data;
	
	public OkResponse() {
		// Success status code는 200이므로 생성자 매개변수로 200을 넘긴다.
		super(200);
	}
	
	public OkResponse (T data) {
		// Success status code는 200이므로 생성자 매개변수로 200을 넘긴다.
		super(200);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
}
