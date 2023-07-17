package com.example.inobao.domain.comment.controller;

import com.example.inobao.domain.comment.dto.CommentRequestDto;
import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postid}/")
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public CommentResponseDto createComment(@PathVariable Long postid, @RequestBody CommentRequestDto commentRequestDto, String email) {
        return commentService.createComment(commentRequestDto, postid, email);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postid, @PathVariable Long commentid, String email) {
        return commentService.deleteComment(postid, commentid, email);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentid}")
    public CommentResponseDto modifyComment(@PathVariable Long postid, @PathVariable Long commentid, @RequestBody CommentRequestDto commentRequestDto, String email) {
        return commentService.modifyComment(postid, commentid, commentRequestDto, email);
    }
}