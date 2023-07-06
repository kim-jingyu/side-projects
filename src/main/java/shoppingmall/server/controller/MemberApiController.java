package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.dto.LogInRequest;
import shoppingmall.server.dto.MemberResponseDto;
import shoppingmall.server.dto.SignUpRequest;
import shoppingmall.server.service.MemberService;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody @Validated LogInRequest logInRequest) {

        return ResponseEntity
                .ok().build();
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody @Validated SignUpRequest memberRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().build();
        }

        MemberResponseDto responseDto = memberService.saveMember(memberRequestDto, passwordEncoder);

        return ResponseEntity
                .ok().body(responseDto);
    }
}
