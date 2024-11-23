package com.ssg.wannavapibackend.common;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),  // 사용자 조회 실패
    PRODUCT_NOT_FOUND(404, "Product not found"),  // 상품 조회 실패
    CART_ITEM_NOT_FOUND(404, "Cart item not found"),  // 장바구니 아이템 조회 실패
    CART_ITEM_ADD_FAILED(500, "Failed to add item to cart"),  // 장바구니 아이템 추가 실패
    CART_ITEM_UPDATE_FAILED(500, "Failed to update cart item"),  // 장바구니 아이템 업데이트 실패
    CART_ITEM_DELETE_FAILED(500, "Failed to delete cart item"),  // 장바구니 아이템 삭제 실패
    CART_ITEM_LIMIT_EXCEEDED(400, "Cannot add more than 15 items to the cart"),  // 장바구니 아이템 수량 초과
    INVALID_PRODUCT_QUANTITY(400, "Quantity for each product must be between 1 and 99"),  // 상품 수량 유효성 검사 실패

    POINT_LOG_NOT_FOUND(404, "Point log not found"),  // 포인트 로그 조회 실패
    INSUFFICIENT_POINTS(400, "Insufficient points for this transaction"),  // 포인트 부족

    PAYMENT_NOT_FOUND(404, "Payment info not found"),  // 결제 정보 조회 실패
    PAYMENT_REQUEST_FAILED(500, "Payment request failed"),  // 결제 요청 실패
    PAYMENT_CONNECTION_FAILED(500, "Payment connection failed"),  // 결제 연결 실패
    PAYMENT_RESPONSE_READ_FAILED(500, "Failed to read payment response"),  // 결제 응답 읽기 실패
    PAYMENT_INVALID_RESPONSE_CODE(400, "Invalid payment response code"),  // 잘못된 결제 응답 코드
    PAYMENT_UNKNOWN_ERROR(500, "Unknown error during payment process"),  // 결제 중 예기치 못한 오류
    PAYMENT_SAVE_FAILED(500, "Failed to save payment info"),  // 결제 정보 저장 실패

    INSUFFICIENT_STOCK(400, "Insufficient stock for the requested product");  // 재고 부족


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
