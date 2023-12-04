package me.jingyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.domain.RefreshToken;
import me.jingyu.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("해당 리프레시 토큰이 없습니다."));
    }
}
