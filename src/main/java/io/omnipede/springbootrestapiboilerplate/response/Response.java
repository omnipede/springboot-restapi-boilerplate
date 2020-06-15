package io.omnipede.springbootrestapiboilerplate.response;

/**
 * API response 에 사용할 클래스.
 */
class Response {
	int status;

	public Response() {
		
	}
	
	public Response(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
