package com.libraryjava.service;

import com.libraryjava.domain.Book;
import com.libraryjava.domain.user.User;
import com.libraryjava.dto.book.BookLoanDto;
import com.libraryjava.dto.book.BookReturnDto;
import com.libraryjava.repository.BookRepository;
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

    public void makeBook(String name) {
        bookRepository.save(new Book(name));
    }

    public void loanBook(BookLoanDto loanDto) {
        User findUser = userRepository.findByName(loanDto.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        bookRepository.findByName(loanDto.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        findUser.loanBook(loanDto.getBookName());
    }

    public void returnBook(BookReturnDto returnDto) {
        User findUser = userRepository.findByName(returnDto.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        findUser.returnBook(returnDto.getBookName());
    }
}
