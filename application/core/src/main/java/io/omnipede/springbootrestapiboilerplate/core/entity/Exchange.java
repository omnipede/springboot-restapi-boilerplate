package io.omnipede.springbootrestapiboilerplate.core.entity;

public class Exchange {

    private final String code;
    private final String name;
    private final String postCode;

    public Exchange(String code, String name, String postCode) {
        this.code = code;
        this.name = name;
        this.postCode = postCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPostCode() {
        return postCode;
    }
}
