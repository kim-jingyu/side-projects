package me.jingyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.domain.User;
import me.jingyu.springbootdeveloper.dto.AddUserRequest;
import me.jingyu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();  // 패스워드 암호화

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // RefreshToken 구현에서 필요
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 아이디로 검색되는 유저가 없습니다."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 이메일로 검색되는 유저가 없습니다."));
    }
}