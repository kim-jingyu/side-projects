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

    @OneToMany(mappedBy = "loanHistories")
    private User user;

    public UserLoanHistory(String bookName) {
        this.bookName = bookName;
    }

    public void returnBook() {
        this.userStatus = UserStatus.IN_ACTIVE;
    }
}
