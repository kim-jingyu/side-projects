package shoppingmall.server.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.entity.Member;
import shoppingmall.server.repository.MemberRepository;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    /**
     * 리소스 서버에서 보내주는 사용자 정보를 불러옴
     * 멤버 테이블에 사용자 정보가 있다면 이름을 업데이트하고, 없다면 회원 데이터 추가
     */
    private void saveOrUpdate(OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String email = (String) attributes.get("email");
        String nickName = (String) attributes.get("nickName");
        Member member = memberRepository.findByEmail(email)
                .map(entity -> entity.updateOAuthMember(nickName))
                .orElse(Member.builder()
                        .email(email)
                        .nickName(nickName)
                        .build());
        memberRepository.save(member);
    }
}
