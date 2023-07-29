package com.myproject.todayhouse.member.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        this("회원이 존재하지 않습니다!");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
