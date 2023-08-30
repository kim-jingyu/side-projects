package com.library.service.book

import com.library.domain.book.Book
import com.library.domain.book.BookRepository
import com.library.domain.user.UserRepository
import com.library.domain.user.loanhistory.UserLoanHistoryRepository
import com.library.domain.user.loanhistory.UserLoanStatus
import com.library.dto.book.BookLoanRequest
import com.library.dto.book.BookRequest
import com.library.dto.book.BookReturnRequest
import com.library.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {
    @Transactional
    fun saveBook(request: BookRequest) {
        val newBook = Book(request.name, request.type)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val book = bookRepository.findByName(request.bookName) ?: fail()
        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("진작 대출되어 있는 책입니다.")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }
}