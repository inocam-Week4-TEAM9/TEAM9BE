package com.example.inobao.domain.like.repository;

import com.example.inobao.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    CommentLike findByCommentIdAndUserId(Long commentId, Long userid);
}
