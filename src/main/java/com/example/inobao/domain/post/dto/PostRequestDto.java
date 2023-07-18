package com.example.inobao.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotBlank(message = "게시글 공백 불가")
    private final String content;

    public PostRequestDto(String content) {
        this.content = content;
    }
}
