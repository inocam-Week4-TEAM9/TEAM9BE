package com.example.inobao.domain.user.controller;

import com.example.inobao.domain.user.dto.UserRequestDto;
import com.example.inobao.domain.user.dto.UserResponseDto;
import com.example.inobao.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signin")
    public UserResponseDto signIn(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.signIn(userRequestDto);
    }

    //중복되면 false
    //중복안되면 true
    @PostMapping("/auth/email")
    public Boolean checkeMail(@RequestParam String email) {
        return !userService.checkEmail(email);
    }

    @PostMapping("/auth/nickname")
    public Boolean checkNickname(@RequestParam String nickname) {
        return !userService.checkNickname(nickname);
    }

}
