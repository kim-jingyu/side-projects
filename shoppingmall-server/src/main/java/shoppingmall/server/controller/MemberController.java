package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shoppingmall.server.service.MemberService;

@Tag(name = "로그인, 로그아웃 페이지", description = "사용자 및 관리자가 로그인, 로그아웃 할 수 있는 페이지입니다.")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "로그인 페이지 뷰")
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @Operation(summary = "OAuth2 로그인 페이지 뷰")
    @GetMapping(value = "/oauth/login")
    public String authLogin() {
        return "auth/login";
    }


    @Operation(summary = "로그아웃")
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
