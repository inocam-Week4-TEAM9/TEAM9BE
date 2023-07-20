package com.example.inobao.domain.post.dto;

import com.example.inobao.domain.comment.dto.CommentResponseDto;
import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.post.entity.PostImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String nickname;
    private final String contents;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private final List<CommentResponseDto> commentList;
    private final List<String> imageUrls;
    private final int likeCount;
    private boolean isLiked;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.contents = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();

        this.commentList = (post.getCommentList() == null) ? null : post.getCommentList().stream().map(CommentResponseDto::new).toList();

        this.imageUrls = (post.getImageUrls() == null) ? null : post.getImageUrls().stream().map(PostImage::getImageUrl).toList();

        this.likeCount = post.getLikeCount();
    }

    public void modifyIsLiked(boolean liked) {
        this.isLiked = liked;
    }
}