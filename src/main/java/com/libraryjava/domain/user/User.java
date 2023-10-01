package com.libraryjava.domain.user;

import com.libraryjava.domain.UserLoanHistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    private final List<UserLoanHistory> loanHistories = new ArrayList<>();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        loanHistories.add(new UserLoanHistory(bookName));
    }

    public void returnBook(String bookName) {
        for (UserLoanHistory loanHistory : loanHistories) {
            if (loanHistory.getBookName().equals(bookName)) {
                loanHistory.returnBook();
            }
        }
    }
}
