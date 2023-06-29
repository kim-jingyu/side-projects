package me.jingyu.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

// 토큰 생성 응답 DTO
@Getter @Setter
public class CreateAccessTokenResponse {
    private String accessToken;
}
