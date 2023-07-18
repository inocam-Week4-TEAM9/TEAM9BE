package com.example.inobao.domain.post.controller;

import com.example.inobao.domain.post.dto.PostRequestDto;
import com.example.inobao.domain.post.dto.PostResponseDto;
import com.example.inobao.domain.post.service.PostService;
import com.example.inobao.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> res = null;
        if (userDetails == null) {
            res = postService.getPosts();
        } else {
            res = postService.getPosts(userDetails.getNickname());
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 게시글 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestPart("content-data") @Valid PostRequestDto postRequestDto,
                                      @RequestPart("images-data") List<MultipartFile> images,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getNickname(), images);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails.getNickname());
    }

    // 게시글 수정
    @PatchMapping("/posts/{postId}")
    public PostResponseDto modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.modifyPost(postRequestDto, postId, userDetails.getNickname());
    }
}
