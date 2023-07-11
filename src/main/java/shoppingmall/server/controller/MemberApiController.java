package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.LogInRequest;
import shoppingmall.server.dto.MemberResponseDto;
import shoppingmall.server.dto.SignUpRequest;
import shoppingmall.server.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping(value = "/user/login")
    public @ResponseBody ResponseEntity userLogin(@Validated LogInRequest logInRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/admin/login")
    public @ResponseBody ResponseEntity adminLogin(@Validated LogInRequest logInRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user/signup")
    public @ResponseBody ResponseEntity userSignup(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        MemberResponseDto responseDto = memberService.saveMember(signUpRequest, Role.USER);

        return ResponseEntity
                .ok().body(responseDto);
    }

    @PostMapping(value = "/admin/signup")
    public @ResponseBody ResponseEntity adminSignup(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(bindingResult.getFieldErrors());
        }

        MemberResponseDto responseDto = memberService.saveMember(signUpRequest, Role.ADMIN);

        return ResponseEntity
                .ok().body(responseDto);
    }
}
