package com.myproject.todayhouse.member.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        this("이미 존재하는 회원입니다.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
