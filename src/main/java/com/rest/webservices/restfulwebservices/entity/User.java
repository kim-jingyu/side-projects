package com.rest.webservices.restfulwebservices.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@ToString
public class User {
    private Long id;
    private String name;
    private LocalDateTime birthDate;
}
