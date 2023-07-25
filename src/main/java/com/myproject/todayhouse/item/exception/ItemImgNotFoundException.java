package com.myproject.todayhouse.item.exception;

public class ItemImgNotFoundException extends RuntimeException{
    public ItemImgNotFoundException(Long itemImgId) {
        this("상품 이미지 아이디 " + itemImgId + " 로 조회된 상품이 없습니다!");
    }

    public ItemImgNotFoundException(String message) {
        super(message);
    }
}
