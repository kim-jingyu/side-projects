package com.libraryjava.dto.user;

import com.libraryjava.domain.user.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private final long id;
    private final String name;
    private final Integer age;

    private UserResponseDto(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getAge());
    }
}
