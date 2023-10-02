package com.libraryjava.service;

import com.libraryjava.domain.user.User;
import com.libraryjava.dto.user.UserMakeDto;
import com.libraryjava.dto.user.UserResponseDto;
import com.libraryjava.dto.user.UserUpdateDto;
import com.libraryjava.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void makeUserTest() {
        // given
        UserMakeDto request = new UserMakeDto("김진규", 1000);

        // when
        userService.makeUser(request);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("김진규");
        assertThat(users.get(0).getAge()).isEqualTo(1000);
    }

    @Test
    void getUsersTest() {
        // given
        userRepository.saveAll(List.of(User.fixture("A", 10), User.fixture("B", 20)));

        // when
        List<UserResponseDto> result = userService.getUsers();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("name").containsExactlyInAnyOrder("A", "B");
        assertThat(result).extracting("age").containsExactlyInAnyOrder(10, 20);
    }

    @Test
    void updateUserTest() {
        // given
        User savedUser = userRepository.save(User.fixture("A", 10));
        UserUpdateDto request = new UserUpdateDto(savedUser.getId(), "B");

        // when
        userService.updateUser(request);

        // then
        User findUser = userRepository.findAll().get(0);
        assertThat(findUser.getName()).isEqualTo("B");
    }

    @Test
    void deleteUserTest() {
        // given
        User savedUser = userRepository.save(User.fixture("A", 10));

        // when
        userService.deleteUser("A");

        // then
        assertThat(userRepository.findAll()).isEmpty();
    }
}