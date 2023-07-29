package com.myproject.todayhouse.order.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        this("해당 주문건을 찾을 수 없습니다!");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
