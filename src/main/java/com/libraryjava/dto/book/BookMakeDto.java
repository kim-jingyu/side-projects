package com.libraryjava.dto.book;

import com.libraryjava.domain.book.BookType;

public record BookMakeDto(String name, BookType type) {
}
