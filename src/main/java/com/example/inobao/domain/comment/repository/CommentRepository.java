package com.example.inobao.domain.comment.repository;

import com.example.inobao.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPostIdAndIdOrderByCreatedAtDesc(Long postId, Long id);
}
