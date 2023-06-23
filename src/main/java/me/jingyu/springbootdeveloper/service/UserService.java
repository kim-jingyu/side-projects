package me.jingyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.domain.User;
import me.jingyu.springbootdeveloper.dto.AddUserRequest;
import me.jingyu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
