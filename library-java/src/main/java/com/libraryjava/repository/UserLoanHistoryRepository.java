package com.libraryjava.repository;

import com.libraryjava.domain.user.loanhistory.UserLoanHistory;
import com.libraryjava.domain.user.loanhistory.UserLoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    Optional<UserLoanHistory> findByBookNameAndUserLoanStatus(String bookName, UserLoanStatus userloanStatus);

    List<UserLoanHistory> findAllByUserLoanStatus(UserLoanStatus userLoanStatus);

    Long countByUserLoanStatus(UserLoanStatus userLoanStatus);
}
