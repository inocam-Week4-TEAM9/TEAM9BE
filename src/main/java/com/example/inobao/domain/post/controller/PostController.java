package com.example.inobao.domain.post.controller;

import com.example.inobao.domain.comment.service.CommentService;
import com.example.inobao.domain.post.dto.PostRequestDto;
import com.example.inobao.domain.post.dto.PostResponseDto;
import com.example.inobao.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> res = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 게시글 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@Valid @RequestBody PostRequestDto postRequestDto, String nickname) {
        return postService.createPost(postRequestDto, nickname);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, String nickname) {
        return postService.deletePost(postId, nickname);
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, String nickname) {
        return postService.modifyPost(postRequestDto, postId, nickname);
    }
}
