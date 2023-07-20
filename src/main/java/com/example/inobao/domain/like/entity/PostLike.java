package com.example.inobao.domain.like.entity;

import com.example.inobao.domain.post.entity.Post;
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
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    private User user;

    @Builder
    private PostLike(Post post, User user) {
        this.user = user;
        this.post = post;
    }
}
