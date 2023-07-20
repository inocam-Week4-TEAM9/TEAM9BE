package com.example.inobao.domain.like.entity;

import com.example.inobao.domain.comment.entity.Comment;
import com.example.inobao.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Entity
@Table(name = "postlikes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @OnDelete(action = CASCADE)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    private User user;

    @Builder
    private CommentLike(Comment comment, User user) {
        this.user = user;
        this.comment = comment;
    }
}
