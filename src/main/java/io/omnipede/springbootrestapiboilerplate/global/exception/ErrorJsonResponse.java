package io.omnipede.springbootrestapiboilerplate.global.exception;

/**
 * API 에러 response 에 사용할 클래스.
 */
public class ErrorJsonResponse {
    // Http status (e.g. 200, 304, 404 ...)
    private int status;
    // Error code
    private String code;
    // Error message
    private String message;

    public ErrorJsonResponse() {

    }

    public ErrorJsonResponse(int status ,String code, String message) {
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 에러 코드 객체를 바탕으로 에러 응답 객체를 생성하는 메소드
     * @param errorCode 에러코드
     * @return 에러 응답 객체
     */
    public static ErrorJsonResponse of(ErrorCode errorCode) {
        return new ErrorJsonResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }
}
