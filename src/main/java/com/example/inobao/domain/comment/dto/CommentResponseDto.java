package com.example.inobao.domain.comment.dto;

import com.example.inobao.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.createdDate = comment.getCreateDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
