package com.example.inobao.domain.post.repository;

import com.example.inobao.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOderByCreatedDateDesc();
}
