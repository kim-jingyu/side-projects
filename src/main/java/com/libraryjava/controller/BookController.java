package com.libraryjava.controller;

import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookMakeDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.dto.book.response.BookStatResponse;
import com.libraryjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<Void> makeBook(@RequestBody BookMakeDto bookMakeDto) {
        bookService.makeBook(bookMakeDto);
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

    @GetMapping("/book/loan")
    public ResponseEntity<Integer> getCountLoanedBook() {
        return ResponseEntity
                .ok(bookService.countLoanedBook());
    }

    @GetMapping("/book/stat")
    public ResponseEntity<List<BookStatResponse>> getBookStatistics() {
        return ResponseEntity
                .ok(bookService.getBookStatistics());
    }
}
