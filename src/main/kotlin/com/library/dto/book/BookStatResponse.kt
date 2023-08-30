package com.library.dto.book

import com.library.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    val count: Long,
) {
}