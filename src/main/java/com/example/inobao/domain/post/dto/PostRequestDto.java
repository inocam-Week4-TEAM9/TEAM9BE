package com.example.inobao.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "게시글 공백 불가")
    private String content;

    public PostRequestDto(String content) {
        this.content = content;
    }
}
