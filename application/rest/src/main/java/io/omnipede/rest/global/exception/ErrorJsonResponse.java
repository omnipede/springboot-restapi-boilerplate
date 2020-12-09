package io.omnipede.rest.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API 에러 response 에 사용할 클래스.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorJsonResponse {
    // Http status (e.g. 200, 304, 404 ...)
    private int status;
    // Error code
    private int code;
    // Error message
    private String message;
    // Field error
    private List<FieldError> errors;

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

    public static ErrorJsonResponse of(ErrorCode errorCode, String specificReason) {
        final List<FieldError> errors = FieldError.of("Specific reason", "", specificReason);
        return of(errorCode, errors);
    }

    public static ErrorJsonResponse of(ErrorCode errorCode, final BindingResult bindingResult) {
        final List<FieldError> errors = FieldError.of(bindingResult);
        return of(errorCode, errors);
    }

    public static ErrorJsonResponse of(ErrorCode errorCode, MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return of(errorCode, errors);
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        /**
         * Change field error to list of field errors
         */
        public static List<FieldError> of (final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        /**
         * Change field errors in binding result to custom field errors
         */
        public static List<FieldError> of (final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}