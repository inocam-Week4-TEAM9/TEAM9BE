package com.example.inobao.domain.user.controller;

import com.example.inobao.domain.user.dto.UserRequestDto;
import com.example.inobao.domain.user.dto.UserResponseDto;
import com.example.inobao.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    boolean check_Email=false;
    boolean check_Nickname=false;
    @PostMapping("/auth/signin")
    public UserResponseDto signIn(@RequestBody UserRequestDto userRequestDto) {
        if (!check_Email)
        {
            if(!check_Nickname) {
                return userService.signIn(userRequestDto);
            }
            else{
                return new UserResponseDto("닉네임 중복확인하세요", HttpStatus.OK.value());
            }
        }
        else
        {
            return new UserResponseDto("이메일 중복확인하세요", HttpStatus.OK.value());
        }
    }

    @PostMapping("/auth/email")
    public Boolean checkeMail(@RequestParam String email){
        check_Email=userService.checkEmail(email);
        return !userService.checkEmail(email);
    }
    @PostMapping("/auth/nickname")
    public Boolean checkNickname(@RequestParam String nickname){
        check_Nickname= userService.checkNickname(nickname);
        return !userService.checkNickname(nickname);
    }
}
