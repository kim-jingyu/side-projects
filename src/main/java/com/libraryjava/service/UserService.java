package com.libraryjava.service;

import com.libraryjava.domain.user.User;
import com.libraryjava.dto.user.UserMakeDto;
import com.libraryjava.dto.user.UserResponseDto;
import com.libraryjava.dto.user.UserUpdateDto;
import com.libraryjava.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::of)
                .toList();
    }

    @Transactional
    public void makeUser(UserMakeDto userMakeDto) {
        userRepository.save(new User(userMakeDto.name(), userMakeDto.age()));
    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findById(userUpdateDto.id())
                .orElseThrow(IllegalArgumentException::new);

        findUser.updateName(userUpdateDto.name());
    }

    @Transactional
    public void deleteUser(String name) {
        User findUser = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(findUser);
    }
}
