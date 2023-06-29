package me.jingyu.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.jingyu.springbootdeveloper.domain.User;
import me.jingyu.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given -> 토큰에 유저 정보를 추가하기 위한 테스트 유저를 만든다.
        User testUser = userRepository.save(User.builder()
                .email("user@email.com")
                .password("test")
                .build());

        // when -> 토큰 제공자의 generateToken 메서드를 호출해서 토큰을 만든다.
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then -> jjwt 라이브러를 사용해 토큰을 복호화한다.
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        Long userId = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만료된 토큰이면 유효성 검증에 실패한다.")
    @Test
    void isValidToken_false() {
        // given -> jjwt 라이브러리를 사용해 이미 만료된 토큰을 생성한다.
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when -> 토큰 제공자로부터 메서드를 호출해 유효한 토큰인지 검증한 뒤 결과값을 반환받는다.
        // then
        assertThat(tokenProvider.validToken(token)).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰이면 유효성 검증에 성공한다.")
    @Test
    void isValidToken_true() {
        // given -> jjwt 라이브러리를 사용해 만료되지 않은 토큰을 생성한다.
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        // then
        assertThat(tokenProvider.validToken(token)).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 받아올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then -> 반환받은 인증 객체의 유저 이름을 가져와 설정한 subject 값과 같은지 확인한다.
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserID(): 토큰 기반으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when -> 토큰을 properties 파일에 저장한 비밀키로 복호한 뒤, 클레임을 가져오는 메서드를 호출해
        // 클레임 정보를 반환받아 클레임에서 id 키로 저장된 값을 가져와 반환한다.
        Long userIdByToken = tokenProvider.getUserId(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}