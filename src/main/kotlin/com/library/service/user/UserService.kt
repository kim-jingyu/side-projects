package com.library.service.user

import com.library.domain.user.User
import com.library.domain.user.UserRepository
import com.library.domain.user.UserStatus
import com.library.domain.user.loanhistory.UserLoanStatus
import com.library.dto.user.*
import com.library.util.fail
import com.library.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val newUser = User(request.name, request.age, UserStatus.ACTIVE)
        userRepository.save(newUser)
    }

    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map { user -> UserResponse.of(user) }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val user = userRepository.findByIdOrThrow(request.id)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories().map {
            user -> UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map {
                    history -> BookHistoryResponse(
                        name = history.bookName,
                        isReturn = history.status == UserLoanStatus.RETURNED,
                    )
                }
            )
        }
    }
}