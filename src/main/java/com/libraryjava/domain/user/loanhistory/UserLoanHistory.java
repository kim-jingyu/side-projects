package com.libraryjava.domain.user.loanhistory;

import com.libraryjava.domain.user.User;
import com.libraryjava.domain.user.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserLoanHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    @Enumerated(EnumType.STRING)
    private UserLoanStatus userLoanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserLoanHistory(String bookName, UserLoanStatus userLoanStatus, User user) {
        this.bookName = bookName;
        this.userLoanStatus = userLoanStatus;
        this.user = user;
    }

    public void doReturn() {
        this.userLoanStatus = UserLoanStatus.RETURNED;
    }
}
