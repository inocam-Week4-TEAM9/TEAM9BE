package com.example.inobao.domain.post.service;

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
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 전체 조회
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOderByCreatedDateDesc()
                .stream().map(PostResponseDto::new).toList();
    }

    // 게시글 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto, String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        Post post = Post.builder()
                .user(user)
                .content(postRequestDto.getContent())
                .build();

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    public ResponseEntity<String> deletePost(Long id, String nickname) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.delete(post);
            return ResponseEntity.ok("삭제 완료");
        }

        if (!post.getUser().getEmail().equals(nickname)) {
            throw new IllegalArgumentException("작성자만 삭제");
        }

        postRepository.delete(post);
        return ResponseEntity.ok("삭제 완료");
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto modifyPost(PostRequestDto postRequestDto, Long id, String nickname) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post.modifyPost(postRequestDto.getContent());
            return new PostResponseDto(post);
        }

        if (!post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정");
        }

        post.modifyPost(postRequestDto.getContent());
        return new PostResponseDto(post);
    }
}
