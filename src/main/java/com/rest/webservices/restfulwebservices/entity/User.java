package com.rest.webservices.restfulwebservices.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Entity(name = "user_details")
public class User {
    @Id
    private Long id;

    @Size(min = 2, message = "이름은 최소 2글자 이상이어야 합니다!")
    private String name;

    @Past(message = "생일은 미래일 수 없습니다!")
    @JsonProperty("birth_date")
    private LocalDateTime birthDate;
}
