package com.libraryjava.dto.user.response;

import java.util.List;

public record UserLoanHistoryResponse(String name, List<BookHistoryResponse> books) {
    public static UserLoanHistoryResponse of(String name, List<BookHistoryResponse> books) {
        return new UserLoanHistoryResponse(name, books);
    }
}
