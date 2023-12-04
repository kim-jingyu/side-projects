package me.jingyu.springbootdeveloper.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

// OAuth2 인증 플로우를 구현하면서 쿠키를 사용할 일이 생기는데, 그때마다 쿠키를 생성하고, 삭제는 로직을 추가하면 불편하므로 유틸리티로 사용할 쿠키 관리 클래스를 미리 구현
public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 HTTP 응답에 쿠키를 추가한다.
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키를 삭제한다. (실제로 삭제하는 방법은 없다. -> 파라미터로 넘어온 키의 쿠키를 빈 값으로 + 만료 시간 0으로 설정)
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    // 객체를 직렬화해 쿠키의 값으로 들어갈 값으로 변환한다.
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키를 역직렬화 객체로 변환한다.
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
