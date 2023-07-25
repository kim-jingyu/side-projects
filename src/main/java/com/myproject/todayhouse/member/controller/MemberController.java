package com.myproject.todayhouse.member.controller;

import com.myproject.todayhouse.member.domain.Role;
import com.myproject.todayhouse.member.dto.request.MemberRequest;
import com.myproject.todayhouse.member.dto.response.MemberResponse;
import com.myproject.todayhouse.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/user/signup")
    public ResponseEntity userSignUp(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(bindingResult);
        }

        MemberResponse memberResponse = memberService.saveMember(memberRequest, Role.USER);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberResponse);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity adminSignUp(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(bindingResult);
        }

        MemberResponse memberResponse = memberService.saveMember(memberRequest, Role.ADMIN);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberResponse);
    }
}
