package com.libraryjava.repository;

import com.libraryjava.domain.UserLoanHistory;
import com.libraryjava.domain.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    Optional<UserLoanHistory> findByBookNameAndUserStatus(String bookName, UserStatus userStatus);
}
