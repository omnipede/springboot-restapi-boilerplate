package io.omnipede.springbootrestapiboilerplate.global.response;

/**
 * API response 에 사용할 클래스.
 */
public class ApiResponse {
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
