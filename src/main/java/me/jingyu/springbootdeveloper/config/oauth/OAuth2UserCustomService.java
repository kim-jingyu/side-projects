package me.jingyu.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.domain.User;
import me.jingyu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 리소스 서버에서 사용자 정보를 불러오는 메서드인 loadUser()를 통해 사용자를 조회하고,
 * 사용자가 users 테이블에 사용자 정보가 있다면 이름을 업데이트하고,
 * 없다면 saveOrUpdate() 메서드를 사용해서 users 테이블에 유저 데이터를 추가
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    // 리소스 서버에서 보내주는 사용자 정보를 불러오는 메서드를 통해 사용자 조회
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체를 반환한다.
        // 사용자 객체는 식별자, 이름, 이메일, 프로필 사진 링크 등의 정보를 담고있다.
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    // 유저가 users 테이블에 정보가 있다면 리소스 서버에서 제공해주는 이름을 업데이트하고, 없다면 이 메서드를 실행해 users 테이블에 새 사용자를 생성해 데이터베이스에 저장
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());
        return userRepository.save(user);
    }
}
