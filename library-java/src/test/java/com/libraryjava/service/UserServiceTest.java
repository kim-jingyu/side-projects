package com.libraryjava.service;

import com.libraryjava.domain.user.User;
import com.libraryjava.domain.user.loanhistory.UserLoanHistory;
import com.libraryjava.domain.user.loanhistory.UserLoanStatus;
import com.libraryjava.dto.user.UserMakeDto;
import com.libraryjava.dto.user.UserResponseDto;
import com.libraryjava.dto.user.UserUpdateDto;
import com.libraryjava.dto.user.response.UserLoanHistoryResponse;
import com.libraryjava.repository.UserLoanHistoryRepository;
import com.libraryjava.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired
    private UserLoanHistoryRepository userLoanHistoryRepository;

    @AfterEach
    void clean() {
        System.out.println("userServiceTest CLEAN");
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

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    void getUserLoanHistoriesTest1() {
        // given
        userRepository.save(User.fixture("A", 10));

        // when
        List<UserLoanHistoryResponse> result = userService.getUserLoanHistories();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("A");
        assertThat(result.get(0).books()).isEmpty();
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다.")
    void getUserLoanHistoriesTest2() {
        // given
        User savedUser = userRepository.save(User.fixture("A", 10));
        userLoanHistoryRepository.saveAll(List.of(
                new UserLoanHistory("책1", UserLoanStatus.LOANED, savedUser),
                new UserLoanHistory("책2", UserLoanStatus.LOANED, savedUser),
                new UserLoanHistory("책3", UserLoanStatus.RETURNED, savedUser)
        ));

        // when
        List<UserLoanHistoryResponse> result = userService.getUserLoanHistories();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("A");
        assertThat(result.get(0).books()).hasSize(3);
        assertThat(result.get(0).books()).extracting("name").containsExactlyInAnyOrder("책1", "책2", "책3");
        assertThat(result.get(0).books()).extracting("isReturn").containsExactlyInAnyOrder(false, false, true);
    }
}