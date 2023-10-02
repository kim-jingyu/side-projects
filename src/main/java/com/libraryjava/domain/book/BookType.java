package com.libraryjava.domain.book;

public enum BookType {
    COMPUTER(10),
    ECONOMY(8),
    SOCIETY(6),
    LANGUAGE(4),
    SCIENCE(2),
    ;

    BookType(int score) {
    }
}
