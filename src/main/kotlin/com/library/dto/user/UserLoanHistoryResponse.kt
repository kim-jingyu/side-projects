package com.library.dto.user

data class UserLoanHistoryResponse(
    val name: String, // 유저 이름
    val books: List<BookHistoryResponse>,
)

data class BookHistoryResponse(
    val name: String, // 책 이름
    val isReturn: Boolean,
)
