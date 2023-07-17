package com.example.inobao.domain.user.service;

import com.example.inobao.domain.user.controller.UserController;
import com.example.inobao.domain.user.dto.UserRequestDto;
import com.example.inobao.domain.user.dto.UserResponseDto;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import com.example.inobao.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String nickname=userRequestDto.getNickname();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        // 사용자 ROLE 확인

        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role) // role 필드 설정 추가
                .build();
        userRepository.save(user);
        return new UserResponseDto("가입 완료",HttpStatus.OK.value());
    }

    public Boolean checkemail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean checknickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
