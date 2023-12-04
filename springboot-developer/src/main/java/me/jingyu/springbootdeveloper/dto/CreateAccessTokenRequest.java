package me.jingyu.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

// 토큰 생성 요청 DTO
@Getter @Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
