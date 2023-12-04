package com.myproject.todayhouse.order.exception;

public class OrderNotValidatedException extends RuntimeException{
    public OrderNotValidatedException() {
        this("주문을 취소할 수 없습니다! 회원 자격 증명을 확인하세요!");
    }

    public OrderNotValidatedException(String message) {
        super(message);
    }
}
