package com.myproject.todayhouse.item.exception;

public class FileStoreException extends RuntimeException{
    public FileStoreException() {
        this("파일 저장 도중에 실패했습니다!");
    }

    public FileStoreException(String message) {
        super(message);
    }
}
