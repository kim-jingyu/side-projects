package com.libraryjava.dto.book.response;

import com.libraryjava.domain.book.BookType;

public record BookStatResponse(BookType type, long count) {
}
