package com.example.inobao.domain.comment.service;

import com.example.inobao.domain.comment.dto.CommentRequestDto;
import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.comment.entity.Comment;
import com.example.inobao.domain.comment.exception.CommentException;
import com.example.inobao.domain.comment.repository.CommentRepository;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.exception.PostException;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import com.example.inobao.domain.user.exception.UserException;
import com.example.inobao.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.inobao.global.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId, String nickname) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostException(NOT_FOUND_DATA)
        );

        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentRequestDto.getContent())
                .build();
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public ResponseEntity<String> deleteComment(Long postId, Long commentId, String nickname) {
        Comment comment = validateAuthority(postId, commentId, nickname);

        commentRepository.delete(comment);
        return ResponseEntity.ok("삭제 완료");
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto modifyComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, String nickname) {
        Comment comment = validateAuthority(postId, commentId, nickname);

        comment.modifyComment(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }

    private Comment validateAuthority(Long postId, Long commentId, String nickname) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(
                () -> new CommentException(NOT_FOUND_DATA)
        );

        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().getId().equals(user.getId())) {
            throw new CommentException(NO_AUTHORITY_TO_DATA);
        }

        return comment;
    }
}