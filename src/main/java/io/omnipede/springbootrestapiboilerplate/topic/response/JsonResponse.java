package io.omnipede.springbootrestapiboilerplate.topic.response;

/**
 * Simeple json response
 */
public class JsonResponse {
    // Http status
    private int status;

    public JsonResponse() {
        this.status = 200;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
