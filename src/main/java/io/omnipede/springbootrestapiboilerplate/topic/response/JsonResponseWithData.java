package io.omnipede.springbootrestapiboilerplate.topic.response;

/**
 * Json response that contain business data
 */
public class JsonResponseWithData<T> extends JsonResponse {

    private T data;

    public JsonResponseWithData(T data) {
        super();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
