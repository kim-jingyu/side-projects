package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.MemberResponseDto;
import shoppingmall.server.dto.SignUpRequest;
import shoppingmall.server.service.MemberService;

@Tag(name = "회원가입 컨트롤러")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "일반 사용자 회원가입")
    @PostMapping(value = "/user/signup")
    public ResponseEntity userSignup(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        MemberResponseDto responseDto = memberService.saveMember(signUpRequest, Role.USER);

        return ResponseEntity
                .ok().body(responseDto);
    }

    @Operation(summary = "관리자 회원가입")
    @PostMapping(value = "/admin/signup")
    public ResponseEntity adminSignup(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        MemberResponseDto responseDto = memberService.saveMember(signUpRequest, Role.ADMIN);

        return ResponseEntity
                .ok().body(responseDto);
    }
}
