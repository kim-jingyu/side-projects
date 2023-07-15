package com.rest.webservices.restfulwebservices.entity;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@ToString
public class User {
    private Long id;

    @Size(min = 2, message = "이름은 최소 2글자 이상이어야 합니다!")
    private String name;
    @Past(message = "생일은 미래일 수 없습니다!")
    private LocalDateTime birthDate;
}
