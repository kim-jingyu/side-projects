package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.SignUpRequest;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Member(String memberName, String email, String password, String address, Role role) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public static Member createUserMember(SignUpRequest requestDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new Member(requestDto.getMemberName(), requestDto.getEmail(), bCryptPasswordEncoder.encode(requestDto.getPassword()), requestDto.getAddress(), Role.USER);
    }

    public static Member createAdminMember(SignUpRequest requestDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new Member(requestDto.getMemberName(), requestDto.getEmail(), bCryptPasswordEncoder.encode(requestDto.getPassword()), requestDto.getAddress(), Role.ADMIN);
    }
}
