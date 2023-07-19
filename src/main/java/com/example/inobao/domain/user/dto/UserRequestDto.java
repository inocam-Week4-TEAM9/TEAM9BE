package com.example.inobao.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank(message = "Email 공백 불가")
    private String email;

    @NotBlank(message = "password 공백 불가")
    private String password;

    @NotBlank(message = "닉네임 공백 불가")
    private String nickname;

    private boolean admin = false;
    private String adminToken;
}
