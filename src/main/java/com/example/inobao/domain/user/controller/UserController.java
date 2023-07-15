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

    @PostMapping("/auth/signin")
    public UserResponseDto signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @PostMapping("/auth/email")
    public Boolean checkemail(@RequestParam String email){
        return userService.checkemail(email);
    }
    @PostMapping("/auth/nickname")
    public Boolean checknickname(@RequestParam String nickname){
        return userService.checknickname(nickname);
    }
}
