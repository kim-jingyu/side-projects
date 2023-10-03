package com.libraryjava.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookHistoryResponse(String name, @JsonProperty("isReturn") boolean isReturn) {
    public static BookHistoryResponse of(String name, boolean isReturn) {
        return new BookHistoryResponse(name, isReturn);
    }
}
