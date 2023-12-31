package com.library.controller.book

import com.library.dto.book.BookLoanRequest
import com.library.dto.book.BookRequest
import com.library.dto.book.BookReturnRequest
import com.library.dto.book.BookStatResponse
import com.library.service.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService
) {
    @PostMapping("/book")
    fun saveBook(@RequestBody request: BookRequest) {
        bookService.saveBook(request)
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: BookLoanRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }

    @GetMapping("/book/loan")
    fun countLoanedBook(): Int {
        return bookService.countLoanedBook()
    }

    // 분야별로 등록되어 있는 책의 권수 보여주기
    @GetMapping("/book/stat")
    fun getBookStatistics() : List<BookStatResponse> {
        return bookService.getBookStatistics()
    }
}