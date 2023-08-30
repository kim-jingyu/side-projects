package com.library.service.book

import com.library.domain.book.Book
import com.library.domain.book.BookRepository
import com.library.domain.user.UserRepository
import com.library.domain.user.loanhistory.UserLoanHistoryRepository
import com.library.domain.user.loanhistory.UserLoanStatus
import com.library.dto.book.BookLoanRequest
import com.library.dto.book.BookRequest
import com.library.dto.book.BookReturnRequest
import com.library.dto.book.BookStatResponse
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

    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    fun getBookStatistics(): List<BookStatResponse> {
//        val results = mutableListOf<BookStatResponse>()
//        val books = bookRepository.findAll()
//        for (book in books) {
//            results.firstOrNull { dto -> book.type == dto.type }?.plusOne() ?: results.add(BookStatResponse(book.type, 1))
//        }
//        return results

        // groupBy 함수형 프로그래밍 이용해서 리팩토링
//        return bookRepository.findAll() // List<Book>
//            .groupBy { book -> book.type }  // Map<BookType, List<Book>>
//            .map { (type, books) -> BookStatResponse(type, books.size) }    // List<BookStatResponse>

        return bookRepository.getStatistics()
    }
}