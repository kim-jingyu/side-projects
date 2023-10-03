package com.libraryjava.dto.user.response;

public record BookHistoryResponse(String name, boolean isReturn) {
    public static BookHistoryResponse of(String name, boolean isReturn) {
        return new BookHistoryResponse(name, isReturn);
    }
}
