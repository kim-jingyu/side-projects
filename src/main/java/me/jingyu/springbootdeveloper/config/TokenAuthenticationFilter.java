package me.jingyu.springbootdeveloper.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 토큰 필터
// -> 요청이 오면 헤더값을 비교해서 토큰이 있는지 확인하고, 유효 토큰이라면 security context holder 에 인증 정보를 저장한다.
// -> 액세스 토큰 값이 담긴 Authorization 헤더값을 가져온뒤 액세스 토큰이 유효하다면 인증 정보를 설정한다.
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization"; // 헤더 정보
    private final static String TOKEN_PREFIX = "Bearer"; // 토큰 prefix

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키의 값을 조회한다.
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 가져온 값에서 prefix를 제거한다.
        String accessToken = getAccessToken(authorizationHeader);

        // 가져온 토큰이 유효한지 확인하고, 유효하면 인증 정보를 관리하는 security context에 인증 정보를 설정한다.
        if (tokenProvider.validToken(accessToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (!authorizationHeader.isEmpty() && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
