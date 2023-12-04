package com.myproject.todayhouse.member.exception;

public class MemberAlreadyExistsException extends RuntimeException{
    public MemberAlreadyExistsException() {
        this("이미 존재하는 회원입니다.");
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
