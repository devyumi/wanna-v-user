package com.ssg.wannavapibackend.common;

public enum ErrorCode {
    USER_NOT_FOUND(404, "The user ID does not exist"),
    PRODUCT_NOT_FOUND(404, "The product does not exist"),
    CART_ADD_FAILED(400, "Failed to add product to cart");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return "[" + code + "]" + " " + message;
    }
}
