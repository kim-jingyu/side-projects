package com.myproject.todayhouse.item.exception;

public class CreateDirectoryException extends RuntimeException{
    public CreateDirectoryException() {
        this("디렉토리 생성에 실패했습니다!");
    }

    public CreateDirectoryException(String message) {
        super(message);
    }
}
