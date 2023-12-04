package shoppingmall.server.config.jwt;

import lombok.Getter;

import java.util.Date;

// JWT 토큰 서비스 테스트 mocking 용 객체
@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
}
