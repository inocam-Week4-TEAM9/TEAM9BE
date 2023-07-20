package com.example.inobao.domain.like.controller;

import com.example.inobao.domain.like.service.LikeService;
import com.example.inobao.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<?> updatePostLike(@PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.updatePostLike(postId, userDetails.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("success", "ture"));
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<?> updateCommentLike(@PathVariable Long commentId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.updateCommentLike(commentId, userDetails.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("success", "ture"));
    }
}
