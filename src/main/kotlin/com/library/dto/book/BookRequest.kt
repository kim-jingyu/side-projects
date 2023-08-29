package com.library.dto.book

import com.library.domain.book.BookType

data class BookRequest(
    var name: String,
    val type: BookType,
)