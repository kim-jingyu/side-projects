package com.myproject.todayhouse.item.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException() {
        this("상품이 존재하지 않습니다!");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
