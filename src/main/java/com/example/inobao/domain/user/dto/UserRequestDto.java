package com.example.inobao.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String email;

    private String password;

    private String nickname;

    private boolean admin = false;
    private String adminToken;
}
