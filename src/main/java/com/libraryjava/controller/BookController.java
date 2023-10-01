package com.libraryjava.controller;

import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<Void> makeBook(@RequestBody String name) {
        bookService.makeBook(name);
        return ResponseEntity
                .ok().build();
    }

    @PostMapping("/book/loan")
    public ResponseEntity<Void> loanBook(@RequestBody BookLoanDto bookLoanDto) {
        try {
            bookService.loanBook(bookLoanDto);
            return ResponseEntity
                    .ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest().build();
        }
    }

    @PutMapping("/book/return")
    public ResponseEntity<Void> returnBook(@RequestBody BookReturnDto bookReturnDto) {
        bookService.returnBook(bookReturnDto);
        return ResponseEntity
                .ok().build();
    }
}
