package me.jingyu.springbootdeveloper.service;

import me.jingyu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// 스프링 시큐리티에서 사용자의 정보를 가져오는 클래스
public class UserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    // 사용자 email로 사용자 정보를 가져오는 클래스
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));
    }
}
