package com.example.inobao.domain.like.service;

import com.example.inobao.domain.comment.entity.Comment;
import com.example.inobao.domain.comment.repository.CommentRepository;
import com.example.inobao.domain.like.entity.CommentLike;
import com.example.inobao.domain.like.entity.PostLike;
import com.example.inobao.domain.like.repository.CommentLikeRepository;
import com.example.inobao.domain.like.repository.PostLikeRepository;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.repository.PostRepository;
import com.example.inobao.domain.user.entity.User;
import com.example.inobao.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void updatePostLike(Long postId, String nickname) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByNickname(nickname).orElseThrow();

        if (!isPostLiked(postId, user.getId())) {
            postLikeRepository.save(
                    PostLike.builder()
                            .post(post)
                            .user(user)
                            .build());
            post.addPostLike();
        } else {
            PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());
            postLikeRepository.delete(postLike);
            post.removePostLike();
        }
    }

    @Transactional
    public void updateCommentLike(Long commentId, String nickname) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = userRepository.findByNickname(nickname).orElseThrow();

        if (!isCommentLiked(commentId, user.getId())) {
            commentLikeRepository.save(
                    CommentLike.builder()
                            .comment(comment)
                            .user(user)
                            .build());
            comment.addCommentLike();
        } else {
            CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId());
            comment.removeCommentLike();
        }
    }

    private boolean isPostLiked(Long postId, Long userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }

    private boolean isCommentLiked(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }
}
