package com.libraryjava.repository;

import com.libraryjava.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.loanHistories")
    List<User> findAllWithHistories();
}
