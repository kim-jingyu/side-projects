package com.libraryjava.service;

import com.libraryjava.domain.book.Book;
import com.libraryjava.domain.user.User;
import com.libraryjava.domain.user.UserStatus;
import com.libraryjava.domain.user.loanhistory.UserLoanStatus;
import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookMakeDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.repository.BookRepository;
import com.libraryjava.repository.UserLoanHistoryRepository;
import com.libraryjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;

    public void makeBook(BookMakeDto makeDto) {
        bookRepository.save(new Book(makeDto.name(), makeDto.type()));
    }

    public void loanBook(BookLoanDto loanDto) {
        if (userLoanHistoryRepository.findByBookNameAndUserLoanStatus(loanDto.bookName(), UserLoanStatus.LOANED).isPresent()) {
            throw new IllegalArgumentException("진작 대출되어 있는 책입니다.");
        }

        User findUser = userRepository.findByName(loanDto.userName())
                .orElseThrow(IllegalArgumentException::new);

        bookRepository.findByName(loanDto.bookName())
                .orElseThrow(IllegalArgumentException::new);

        findUser.loanBook(loanDto.bookName());
    }

    public void returnBook(BookReturnDto returnDto) {
        User findUser = userRepository.findByName(returnDto.userName())
                .orElseThrow(IllegalArgumentException::new);

        findUser.returnBook(returnDto.bookName());
    }
}
