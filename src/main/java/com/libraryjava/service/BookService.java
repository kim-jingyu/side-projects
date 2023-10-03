package com.libraryjava.service;

import com.libraryjava.domain.book.Book;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

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

    @Transactional(readOnly = true)
    public int countLoanedBook() {
//        return userLoanHistoryRepository.findAllByUserLoanStatus(UserLoanStatus.LOANED).size();
        return userLoanHistoryRepository.countByUserLoanStatus(UserLoanStatus.LOANED).intValue();
    }

    @Transactional(readOnly = true)
    public List<BookStatResponse> getBookStatistics() {
//        Map<BookType, List<Book>> map = bookRepository.findAll().stream()
//                .collect(groupingBy(book -> book.getType()));
//
//        List<BookStatResponse> result = new ArrayList<>();
//        for (BookType bookType : map.keySet()) {
//            result.add(new BookStatResponse(bookType, map.get(bookType).size()));
//        }
//        return result;

//        List<BookStatResponse> responses = new ArrayList<>();
//        List<Book> books = bookRepository.findAll();
//        for (Book book : books) {
//            responses.stream()
//                    .filter(dto -> dto.getBookType() == book.getType())
//                    .findFirst()
//                    .ifPresentOrElse(BookStatResponse::plusOne, () -> responses.add(new BookStatResponse(book.getType(), 1)));
//        }
        return bookRepository.getStats();
    }
}
