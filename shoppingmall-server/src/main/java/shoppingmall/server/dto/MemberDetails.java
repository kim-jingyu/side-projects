package shoppingmall.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shoppingmall.server.entity.Member;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class MemberDetails implements UserDetails {
    private final Member member;

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole().toString()));
    }

    // 사용자의 id (고유값)
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;    // 계정이 만료되었는지 확인 (만료X)
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;    // 계정이 잠금되었는지 확인 (만료X)
    }

    // 패스워드 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;        // 패스워드 만료 여부 확인 (만료X)
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
