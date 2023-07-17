package com.example.inobao.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "게시글 공백 불가")
    private final String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
