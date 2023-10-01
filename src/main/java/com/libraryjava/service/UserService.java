package com.libraryjava.service;

import com.libraryjava.domain.user.User;
import com.libraryjava.dto.user.UserMakeDto;
import com.libraryjava.dto.user.UserUpdateDto;
import com.libraryjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void makeUser(UserMakeDto userMakeDto) {
        userRepository.save(new User(userMakeDto.getName(), userMakeDto.getAge()));
    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(IllegalArgumentException::new);

        findUser.updateName(userUpdateDto.getName());
    }

    @Transactional
    public void deleteUser(String name) {
        User findUser = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(findUser);
    }
}
