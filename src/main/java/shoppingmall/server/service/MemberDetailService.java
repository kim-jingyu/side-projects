package shoppingmall.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shoppingmall.server.dto.MemberDetails;
import shoppingmall.server.entity.Member;
import shoppingmall.server.repository.MemberRepository;

// 스프링 시큐리티에서 사용자의 정보를 가져오는 클래스
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return User
                .builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
