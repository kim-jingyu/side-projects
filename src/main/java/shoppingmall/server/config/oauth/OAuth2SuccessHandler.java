package shoppingmall.server.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import shoppingmall.server.config.jwt.TokenProvider;
import shoppingmall.server.entity.Member;
import shoppingmall.server.entity.RefreshToken;
import shoppingmall.server.repository.RefreshTokenRepository;
import shoppingmall.server.service.MemberService;
import shoppingmall.server.utils.CookieUtil;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/";

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = memberService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        // 리프레시 토큰 생성
        String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);

        // 데이터베이스에 저장
        saveRefreshToken(member, refreshToken);

        // 쿠키에 저장
        addRefreshTokenToCookie(request, response, refreshToken);

        // 액세스 토큰 생성
        String accessToken = tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION);

        // 패스에 추가
        String targetUrl = UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", accessToken)
                .build()
                .toUriString();

        // 인증 관련 설정값 및 쿠키 제거
        super.clearAuthenticationAttributes(request);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequest(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void saveRefreshToken(Member member, String refreshToken) {
        RefreshToken madeRefreshToken = refreshTokenRepository.findByUserId(member.getMemberId())
                .map(entity -> entity.update(refreshToken))
                .orElse(new RefreshToken(member.getMemberId(), refreshToken));

        refreshTokenRepository.save(madeRefreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }
}
