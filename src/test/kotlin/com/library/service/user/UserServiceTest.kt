package com.library.service.user

import com.library.domain.user.User
import com.library.domain.user.UserRepository
import com.library.dto.user.UserCreateRequest
import com.library.dto.user.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
) {
    @AfterEach
    fun clear() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장 테스트")
    fun saveUserTest() {
        // given
        val request = UserCreateRequest("유저1", null)

        // when
        userService.saveUser(request)

        // then
        val users = userRepository.findAll()
        assertThat(users).hasSize(1)
    }

    @Test
    @DisplayName("유저 리스트 조회 테스트")
    fun getUsersTest() {
        // given
        userRepository.saveAll(listOf(
            User.fixture("유저1", 20),
            User.fixture("유저2", null),
        ))

        // when
        val users = userService.getUsers()

        // then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("유저1", "유저2")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, null)
    }
    
    @Test
    @DisplayName("유저 이름 업데이트 테스트")
    fun updateUserNameTest() {
        // given
        val savedUser = userRepository.save(User.fixture("유저1"))

        // when
        userService.updateUserName(UserUpdateRequest(savedUser.id!!, "유저2"))
        
        // then
        val user = userRepository.findAll()[0]
        assertThat(user.name).isEqualTo("유저2")
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun deleteUserTest() {
        // given
        userRepository.save(User.fixture("유저1"))

        // when
        userService.deleteUser("유저1")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }
}