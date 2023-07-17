package com.example.inobao.domain.comment.service;

import com.example.inobao.domain.comment.dto.CommentRequestDto;
import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.comment.entity.Comment;
import com.example.inobao.domain.comment.repository.CommentRepository;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long id, String email) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException());

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentRequestDto.getContent())
                .build();
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public ResponseEntity<String> deleteComment(Long postid, Long id, String email) {
        Comment comment = commentRepository.findByPostIdAndIdOrderByCreatedAtDesc(postid, id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException());

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
    public CommentResponseDto modifyComment(Long postid, Long id, CommentRequestDto commentRequestDto, String email) {
        Comment comment = commentRepository.findByPostIdAndIdOrderByCreatedAtDesc(postid, id).orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException());

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.modifyComment(commentRequestDto.getContent());
            return new CommentResponseDto(comment);
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작설자만 수정");
        }

        comment.modifyComment(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }
}