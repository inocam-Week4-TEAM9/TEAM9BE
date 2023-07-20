package com.example.inobao.domain.comment.controller;

import com.example.inobao.domain.comment.dto.CommentRequestDto;
import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.comment.service.CommentService;
import com.example.inobao.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/")
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, postId, userDetails.getNickname());
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(postId, commentId, userDetails.getNickname());
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public CommentResponseDto modifyComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(postId, commentId, commentRequestDto, userDetails.getNickname());
    }
}
