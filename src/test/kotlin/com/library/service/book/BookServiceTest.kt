package com.library.service.book

import com.library.domain.book.Book
import com.library.domain.book.BookRepository
import com.library.domain.user.User
import com.library.domain.user.UserRepository
import com.library.domain.user.loanhistory.UserLoanHistoryRepository
import com.library.dto.book.BookLoanRequest
import com.library.dto.book.BookRequest
import com.library.dto.book.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val loanHistoryRepository: UserLoanHistoryRepository,
) {
    @AfterEach
    fun clear() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
        loanHistoryRepository.deleteAll()
    }

    @Test
    @DisplayName("책 저장 테스트")
    fun saveBookTest() {
        // given
        val request = BookRequest("책1")

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
    }

    @Test
    @DisplayName("책 대출 성공 테스트")
    fun loanBookSuccessTest() {
        // given
        bookRepository.save(Book("책1"))
        userRepository.save(User("유저1", null))
        val bookLoanRequest = BookLoanRequest("책1", "유저1")

        // when
        bookService.loanBook(bookLoanRequest)

        // then
        val loanHistories = loanHistoryRepository.findAll()
        assertThat(loanHistories).hasSize(1)
        assertThat(loanHistories[0].bookName).isEqualTo("책1")
        assertThat(loanHistories[0].isReturn).isFalse()
        assertThat(loanHistories[0].user.name).isEqualTo("유저1")
    }

    @Test
    @DisplayName("책 대출 실패 테스트")
    fun loanBookFailTest() {
        // given
        bookRepository.save(Book("책1"))
        userRepository.save(User("유저1", null))
        userRepository.save(User("유저2", null))
        val bookLoanRequest1 = BookLoanRequest("책1", "유저1")
        val bookLoanRequest2 = BookLoanRequest("책1", "유저2")

        // when
        bookService.loanBook(bookLoanRequest1)

        // then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(bookLoanRequest2)
        }.apply {
            assertThat(message).isEqualTo("진작 대출되어 있는 책입니다.")
        }
    }

    @Test
    @DisplayName("책 대출 반납 테스트")
    fun returnBookTest() {
        // given
        bookRepository.save(Book("책1"))
        userRepository.save(User("유저1", null))
        val bookLoanRequest = BookLoanRequest("책1", "유저1")
        bookService.loanBook(bookLoanRequest)
        val bookReturnRequest = BookReturnRequest("유저1", "책1")

        // when
        bookService.returnBook(bookReturnRequest)

        // then
        val loanHistories = loanHistoryRepository.findAll()
        assertThat(loanHistories).hasSize(1)
        assertThat(loanHistories[0].isReturn).isTrue()
    }
}