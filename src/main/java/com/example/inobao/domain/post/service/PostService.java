package com.example.inobao.domain.post.service;

import com.example.inobao.domain.like.repository.CommentLikeRepository;
import com.example.inobao.domain.like.repository.PostLikeRepository;
import com.example.inobao.domain.post.dto.PostRequestDto;
import com.example.inobao.domain.post.dto.PostResponseDto;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import com.example.inobao.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 게시글 전체 조회
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponse = posts.stream()
                .map(PostResponseDto::new)
                .toList();
        return postResponse;
    }

    public List<PostResponseDto> getPosts(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow();

        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponse = posts.stream()
                .map(PostResponseDto::new)
                .peek(dto -> dto.modifyIsLiked(postLikeRepository.existsByPostIdAndUserId(dto.getId(), user.getId())))
                .peek(dto -> dto.getCommentList().forEach(cDto -> cDto.modifyIsLiked(commentLikeRepository.existsByCommentIdAndUserId(cDto.getId(), user.getId()))))
                .toList();
        return postResponse;
    }

    // 게시글 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto, String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        Post post = Post.builder()
                .user(user)
                .content(postRequestDto.getContent())
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(Long id, String nickname) {
        Post post = validationAuthority(id, nickname);

        postRepository.delete(post);
        return ResponseEntity.ok("삭제 완료");
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto modifyPost(PostRequestDto postRequestDto, Long id, String nickname) {
        Post post = validationAuthority(id, nickname);

        post.modifyPost(postRequestDto.getContent());
        return new PostResponseDto(post);
    }

    private Post validationAuthority(Long id, String nickname) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN) || (post.getUser().getId().equals(user.getId()))) {
            return post;
        }
        throw new IllegalArgumentException("삭제 권한 없음");
    }
}