package com.ssg.wannavapibackend.common;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    PRODUCT_NOT_FOUND(404, "Product not found"),
    CART_ITEM_NOT_FOUND(404, "Cart item not found"),
    CART_ITEM_ADD_FAILED(500, "Failed to add item to cart"),
    CART_ITEM_UPDATE_FAILED(500, "Failed to update cart item"),
    CART_ITEM_DELETE_FAILED(500, "Failed to delete cart item"),
    CART_ITEM_LIMIT_EXCEEDED(400, "Cannot add more than 15 items to the cart"), // 최대 15개의 상품만 장바구니에 담을 수 있는 에러 코드
    INVALID_PRODUCT_QUANTITY(400, "Quantity for each product must be between 1 and 99"), // 하나의 상품 수량이 1부터 99 사이여야 하는 에러 코드

    PAYMENT_REQUEST_FAILED(500, "Payment request failed"), // 결제 요청 실패
    PAYMENT_CONNECTION_FAILED(500, "Payment connection failed"), // 결제 연결 실패
    PAYMENT_RESPONSE_READ_FAILED(500, "Failed to read payment response"), // 응답 읽기 실패
    PAYMENT_INVALID_RESPONSE_CODE(400, "Invalid payment response code"), // 잘못된 응답 코드
    PAYMENT_UNKNOWN_ERROR(500, "Unknown error during payment process"); // 예기치 못한 결제 오류;


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
