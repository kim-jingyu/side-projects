package com.libraryjava.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private BookType type;

    public Book(String name, BookType type) {
        this.name = name;
        this.type = type;
    }

    public static Book fixture(String name, BookType type) {
        return new Book(name, type);
    }
}
