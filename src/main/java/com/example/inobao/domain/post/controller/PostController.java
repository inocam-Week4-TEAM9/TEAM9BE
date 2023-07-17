package com.example.inobao.domain.post.controller;

import com.example.inobao.domain.comment.service.CommentService;
import com.example.inobao.domain.post.dto.PostRequestDto;
import com.example.inobao.domain.post.dto.PostResponseDto;
import com.example.inobao.domain.post.service.PostService;
import com.example.inobao.global.jwt.JwtUtil;
import com.example.inobao.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    public static final Logger log = LoggerFactory.getLogger("JWT 관련 로그");

    UserDetailsImpl userDetails;
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> res = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 게시글 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody @Valid PostRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getNickname());
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails.getNickname());
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.modifyPost(postRequestDto, postId, userDetails.getNickname());
    }
}
