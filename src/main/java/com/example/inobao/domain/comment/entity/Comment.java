package com.example.inobao.domain.comment.entity;

import com.example.inobao.domain.post.entity.Post;
import com.example.inobao.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotBlank(message = "댓글 공백 불가")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedDate;

    @Builder
    private Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void modifyComment(String content) { this.content = content; }
}
