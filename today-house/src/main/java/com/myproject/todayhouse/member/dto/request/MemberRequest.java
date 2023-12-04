package com.myproject.todayhouse.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberRequest {
    @Email @NotBlank
    private String email;
    @NotBlank
    @Min(value = 8)
    private String password;
    @NotBlank
    @Length(min = 2, max = 15)
    private String nickName;
}
