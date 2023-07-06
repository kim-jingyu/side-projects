package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.SignUpRequest;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

    @Column(unique = true)
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

    public static Member createMember(SignUpRequest requestDto, PasswordEncoder passwordEncoder) {
        return new Member(requestDto.getMemberName(), requestDto.getEmail(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getAddress(), Role.USER);
    }
}
