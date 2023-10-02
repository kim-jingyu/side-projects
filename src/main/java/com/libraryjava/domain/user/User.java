package com.libraryjava.domain.user;

import com.libraryjava.domain.UserLoanHistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@ToString(of = {"name", "age"})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<UserLoanHistory> loanHistories = new ArrayList<>();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static User fixture(String name, int age) {
        return new User(name, age);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        loanHistories.add(new UserLoanHistory(bookName, UserStatus.ACTIVE, this));
    }

    public void returnBook(String bookName) {
        for (UserLoanHistory loanHistory : loanHistories) {
            if (loanHistory.getBookName().equals(bookName)) {
                loanHistory.doReturn();
            }
        }
    }
}
