package com.ssg.wannavapibackend.common;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    PRODUCT_NOT_FOUND(404, "Product not found"),
    CART_ITEM_NOT_FOUND(404, "Cart item not found"),
    CART_ITEM_ADD_FAILED(500, "Failed to add item to cart"),
    CART_ITEM_UPDATE_FAILED(500, "Failed to update cart item"),
    CART_ITEM_DELETE_FAILED(500, "Failed to delete cart item"),
    // 최대 15개의 상품만 장바구니에 담을 수 있는 에러 코드
    CART_ITEM_LIMIT_EXCEEDED(400, "Cannot add more than 15 items to the cart"),
    // 하나의 상품 수량이 1부터 99 사이여야 하는 에러 코드
    INVALID_PRODUCT_QUANTITY(400, "Quantity for each product must be between 1 and 99");


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
