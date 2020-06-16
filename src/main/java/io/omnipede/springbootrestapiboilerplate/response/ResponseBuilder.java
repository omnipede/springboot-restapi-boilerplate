package io.omnipede.springbootrestapiboilerplate.response;

import java.util.HashMap;

public class ResponseBuilder {
    private HashMap<String, Object> map;
    private int status;

    public ResponseBuilder() {
        map = new HashMap<>();
    }
    public ResponseBuilder status(int status) {
        this.status = status;
        return this;
    }
}
