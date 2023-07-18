package com.example.inobao.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "댓글 공백 불가")
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
