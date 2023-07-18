package com.example.inobao.domain.like.repository;

import com.example.inobao.domain.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    PostLike findByPostIdAndUserId(Long postId, Long userId);
}
