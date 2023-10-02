package com.libraryjava.service;

import com.libraryjava.domain.Book;
import com.libraryjava.domain.UserLoanHistory;
import com.libraryjava.domain.user.User;
import com.libraryjava.domain.user.UserStatus;
import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookMakeDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.repository.BookRepository;
import com.libraryjava.repository.UserLoanHistoryRepository;
import com.libraryjava.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLoanHistoryRepository userLoanHistoryRepository;


    @AfterEach
    void clear() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    void makeBookTest() {
        // given
        BookMakeDto request = new BookMakeDto("이상한 나라의 앨리스", "동화");

        // when
        bookService.makeBook(request);

        // then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    @DisplayName("책 대출 성공 테스트")
    void loanBookSuccessTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", "소설"));
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        BookLoanDto request = new BookLoanDto("김진규", "이상한 나라의 앨리스");

        // when
        bookService.loanBook(request);

        // then
        List<UserLoanHistory> results = userLoanHistoryRepository.findAll();
        assertThat(results).hasSize(1);
        System.out.println("results.get(0) = " + results.get(0));
        assertThat(results.get(0).getBookName()).isEqualTo("이상한 나라의 앨리스");
        System.out.println("results.get(0) = " + results.get(0));
        assertThat(results.get(0).getUser().getId()).isEqualTo(savedUser.getId());
        System.out.println("results.get(0) = " + results.get(0));
        assertThat(results.get(0).getUserStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("책 대출 실패 테스트")
    void loanBookFailTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", "소설"));
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        userLoanHistoryRepository.save(new UserLoanHistory("이상한 나라의 앨리스", UserStatus.ACTIVE, savedUser));
        BookLoanDto request = new BookLoanDto("김진규", "이상한 나라의 앨리스");

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.loanBook(request), "진작 대출되어 있는 책입니다.");
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    void returnBookTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", "소설"));
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        userLoanHistoryRepository.save(new UserLoanHistory("이상한 나라의 앨리스", UserStatus.ACTIVE, savedUser));
        BookReturnDto request = new BookReturnDto("김진규", "이상한 나라의 앨리스");

        // when
        bookService.returnBook(request);

        // then
        List<UserLoanHistory> results = userLoanHistoryRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUserStatus()).isEqualTo(UserStatus.IN_ACTIVE);
    }
}