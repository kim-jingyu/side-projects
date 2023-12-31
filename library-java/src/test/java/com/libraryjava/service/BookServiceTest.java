package com.libraryjava.service;

import com.libraryjava.domain.book.Book;
import com.libraryjava.domain.user.loanhistory.UserLoanHistory;
import com.libraryjava.domain.book.BookType;
import com.libraryjava.domain.user.User;
import com.libraryjava.domain.user.UserStatus;
import com.libraryjava.domain.user.loanhistory.UserLoanStatus;
import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookMakeDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.dto.book.response.BookStatResponse;
import com.libraryjava.repository.BookRepository;
import com.libraryjava.repository.UserLoanHistoryRepository;
import com.libraryjava.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        BookMakeDto request = new BookMakeDto("이상한 나라의 앨리스", BookType.COMPUTER);

        // when
        bookService.makeBook(request);

        // then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getName()).isEqualTo("이상한 나라의 앨리스");
        assertThat(books.get(0).getType()).isEqualTo(BookType.COMPUTER);
    }

    @Test
    @DisplayName("책 대출 성공 테스트")
    void loanBookSuccessTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", BookType.COMPUTER));
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
        assertThat(results.get(0).getUserLoanStatus()).isEqualTo(UserLoanStatus.LOANED);
    }

    @Test
    @DisplayName("책 대출 실패 테스트")
    void loanBookFailTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", BookType.ECONOMY));
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        userLoanHistoryRepository.save(new UserLoanHistory("이상한 나라의 앨리스", UserLoanStatus.LOANED, savedUser));
        BookLoanDto request = new BookLoanDto("김진규", "이상한 나라의 앨리스");

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.loanBook(request), "진작 대출되어 있는 책입니다.");
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    void returnBookTest() {
        // given
        bookRepository.save(Book.fixture("이상한 나라의 앨리스", BookType.COMPUTER));
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        userLoanHistoryRepository.save(new UserLoanHistory("이상한 나라의 앨리스", UserLoanStatus.LOANED, savedUser));
        BookReturnDto request = new BookReturnDto("김진규", "이상한 나라의 앨리스");

        // when
        bookService.returnBook(request);

        // then
        List<UserLoanHistory> results = userLoanHistoryRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUserLoanStatus()).isEqualTo(UserLoanStatus.RETURNED);
    }

    @Test
    @DisplayName("책 대여 권수를 확인한다.")
    void countLoanedBookTest() {
        // given
        User savedUser = userRepository.save(User.fixture("김진규", 10));
        userLoanHistoryRepository.saveAll(List.of(
                new UserLoanHistory("이상한 나라의 앨리스", UserLoanStatus.LOANED, savedUser),
                new UserLoanHistory("토비의 스프링", UserLoanStatus.LOANED, savedUser),
                new UserLoanHistory("해리포터", UserLoanStatus.LOANED, savedUser)));

        // when
        int count = bookService.countLoanedBook();

        // then
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("분야별 책 권수를 확인한다.")
    void getBookStatisticsTest() {
        // given
        bookRepository.saveAll(List.of(
                Book.fixture("이상한 나라의 앨리스", BookType.SOCIETY),
                Book.fixture("토비의 스프링", BookType.COMPUTER),
                Book.fixture("해리포터", BookType.SOCIETY),
                Book.fixture("양자역학", BookType.SCIENCE)
        ));

        // when
        List<BookStatResponse> result = bookService.getBookStatistics();

        // then
        assertThat(result).hasSize(3);
        assertCount(result, BookType.COMPUTER, 1L);
        assertCount(result, BookType.SCIENCE, 1L);
        assertCount(result, BookType.SOCIETY, 2L);
    }

    private void assertCount(List<BookStatResponse> result, BookType type, long expectedCount) {
        assertThat(result.stream()
                .filter(dto -> dto.type() == type)
                .findFirst().orElseThrow(IllegalStateException::new).count())
                .isEqualTo(expectedCount);
    }
}