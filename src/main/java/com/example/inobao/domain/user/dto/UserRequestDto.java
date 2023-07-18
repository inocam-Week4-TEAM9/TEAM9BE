package com.example.inobao.domain.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String email;

    private String password;

    private String nickname;

    private boolean admin = false;
    private String adminToken;
}
