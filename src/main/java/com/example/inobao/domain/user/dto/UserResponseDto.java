package com.example.inobao.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String msg;
    private int status;
//    public UserResponseDto(String msg,int status){
//        this.msg=msg;
//        this.status=status;
//    }
}
