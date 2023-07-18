package com.example.inobao.domain.comment.service;

import com.example.inobao.domain.comment.dto.CommentRequestDto;
import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.comment.entity.Comment;
import com.example.inobao.domain.comment.repository.CommentRepository;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import com.example.inobao.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId, String nickname) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

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
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.delete(comment);
            return ResponseEntity.ok("삭제 완료");
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 삭제");
        }

        commentRepository.delete(comment);
        return ResponseEntity.ok("삭제 완료");
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto modifyComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, String nickname) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.modifyComment(commentRequestDto.getContent());
            return new CommentResponseDto(comment);
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정");
        }

        comment.modifyComment(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }
}