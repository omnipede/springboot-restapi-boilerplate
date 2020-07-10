package io.omnipede.springbootrestapiboilerplate.topic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Json response that contain business data
 */
@Getter
@Setter
@AllArgsConstructor
public class JsonResponseWithData<T> extends JsonResponse {
    // Data to send with server response
    private T data;
}
