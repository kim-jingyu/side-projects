package com.library.dto.book

data class BookLoanRequest(
    val bookName: String,
    val userName: String,
)