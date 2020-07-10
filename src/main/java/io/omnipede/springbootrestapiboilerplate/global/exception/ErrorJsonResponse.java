package io.omnipede.springbootrestapiboilerplate.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    // Field error
    private List<FieldError> errors;

    public ErrorJsonResponse() {}

    public ErrorJsonResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

    public ErrorJsonResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = new ArrayList<>();
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

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    /**
     * 에러 코드 객체를 바탕으로 에러 응답 객체를 생성하는 메소드
     * @param errorCode 에러코드
     * @return 에러 응답 객체
     */
    public static ErrorJsonResponse of(ErrorCode errorCode) {
        return new ErrorJsonResponse(errorCode);
    }

    public static ErrorJsonResponse of(ErrorCode errorCode, final List<FieldError> errors) {
        return new ErrorJsonResponse(errorCode, errors);
    }

    public static ErrorJsonResponse of(ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorJsonResponse(errorCode, FieldError.of(bindingResult));
    }

    public static ErrorJsonResponse of(ErrorCode errorCode, MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorJsonResponse(errorCode, errors);
    }

    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError() {}

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of (final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        public static List<FieldError> of (final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}