package com.libraryjava.repository;

import com.libraryjava.domain.user.loanhistory.UserLoanHistory;
import com.libraryjava.domain.user.loanhistory.UserLoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    Optional<UserLoanHistory> findByBookNameAndUserLoanStatus(String bookName, UserLoanStatus userloanStatus);
}
