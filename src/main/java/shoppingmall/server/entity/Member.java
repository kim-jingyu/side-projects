package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.SignUpRequest;

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

    // OAuth2 - 사용자 이름
    @Column(unique = true)
    private String nickName;

    private Member(String memberName, String email, String password, String address, Role role) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    @Builder
    private Member(String email, Role role, String nickName) {
        this.email = email;
        this.role = Role.USER;
        this.nickName = nickName;
    }

    public static Member createUserMember(SignUpRequest requestDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new Member(requestDto.getMemberName(), requestDto.getEmail(), bCryptPasswordEncoder.encode(requestDto.getPassword()), requestDto.getAddress(), Role.USER);
    }

    public static Member createAdminMember(SignUpRequest requestDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new Member(requestDto.getMemberName(), requestDto.getEmail(), bCryptPasswordEncoder.encode(requestDto.getPassword()), requestDto.getAddress(), Role.ADMIN);
    }

    public Member updateOAuthMember(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
