package com.myproject.todayhouse.item.exception;

public class FileDeleteException extends RuntimeException{
    public FileDeleteException() {
        this("파일 삭제 도중에 실패했습니다!");
    }

    public FileDeleteException(String message) {
        super(message);
    }
}
