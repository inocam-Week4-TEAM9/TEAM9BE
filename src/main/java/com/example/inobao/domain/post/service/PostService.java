package com.example.inobao.domain.post.service;

import com.example.inobao.domain.comment.exception.CommentException;
import com.example.inobao.domain.like.repository.CommentLikeRepository;
import com.example.inobao.domain.like.repository.PostLikeRepository;
import com.example.inobao.domain.post.dto.PostRequestDto;
import com.example.inobao.domain.post.dto.PostResponseDto;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.entity.PostImage;
import com.example.inobao.domain.post.exception.PostException;
import com.example.inobao.domain.post.repository.PostImageRepository;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import com.example.inobao.domain.user.exception.UserException;
import com.example.inobao.domain.user.repository.UserRepository;
import com.example.inobao.global.utils.aws.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.inobao.global.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostImageRepository postImageRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final S3Utils s3Utils;

    // 게시글 전체 조회
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDateDesc();
        List<PostResponseDto> postResponse = posts.stream()
                .map(PostResponseDto::new)
                .toList();
        return postResponse;
    }

    public List<PostResponseDto> getPosts(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow();

        List<Post> posts = postRepository.findAllByOrderByCreatedDateDesc();
        List<PostResponseDto> postResponse = posts.stream()
                .map(PostResponseDto::new)
                .peek(dto -> dto.modifyIsLiked(postLikeRepository.existsByPostIdAndUserId(dto.getId(), user.getId())))
                .peek(dto -> dto.getCommentList().forEach(cDto -> cDto.modifyIsLiked(commentLikeRepository.existsByCommentIdAndUserId(cDto.getId(), user.getId()))))
                .toList();
        return postResponse;
    }

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String nickname, List<MultipartFile> images) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );

        Post post = Post.builder()
                .user(user)
                .content(postRequestDto.getContent())
                .build();

        Post savedPost = postRepository.save(post);


        if (!(images == null)) {
            List<String> imageUrls = s3Utils.uploadFile(images);
            for (String imageUrl : imageUrls) {
                PostImage postImage = PostImage.builder()
                        .post(savedPost)
                        .imageUrl(imageUrl)
                        .build();
                postImageRepository.save(postImage);
            }
        }

        return new PostResponseDto(savedPost);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(Long id, String nickname) {
        Post post = validateAuthority(id, nickname);

        postRepository.delete(post);
        return ResponseEntity.ok("삭제 완료");
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto modifyPost(PostRequestDto postRequestDto, Long id, String nickname) {
        Post post = validateAuthority(id, nickname);

        post.modifyPost(postRequestDto.getContent());
        return new PostResponseDto(post);
    }

    private Post validateAuthority(Long id, String nickname) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostException(NOT_FOUND_DATA)
        );

        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !post.getUser().getId().equals(user.getId())) {
            throw new CommentException(NO_AUTHORITY_TO_DATA);
        }

        return post;
    }
}