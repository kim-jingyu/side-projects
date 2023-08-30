package com.library.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String) : User?

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
    fun findAllWithHistories() : List<User>
}