package io.omnipede.springbootrestapiboilerplate.domain.topic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Simeple json response
 */
@Getter
@Setter
@AllArgsConstructor
public class JsonResponse {
    // Http status
    private int status;
    public JsonResponse() {
        this.status = 200;
    }
}
