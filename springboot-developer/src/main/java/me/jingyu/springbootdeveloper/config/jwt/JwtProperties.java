package me.jingyu.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티 값을 가져와서 사용하는 애노테이션
public class JwtProperties {    // 설정 파일에 선언된 jwt 토큰 관련 값들을 변수로 접근하는데 사용할 클래스
    private String issuer;
    private String secretKey;
}
