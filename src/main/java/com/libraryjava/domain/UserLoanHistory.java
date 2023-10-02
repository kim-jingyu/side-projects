package com.libraryjava.domain;

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
    private UserStatus userStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserLoanHistory(String bookName, UserStatus userStatus, User user) {
        this.bookName = bookName;
        this.userStatus = userStatus;
        this.user = user;
    }

    public void doReturn() {
        this.userStatus = UserStatus.IN_ACTIVE;
    }
}
