package com.example.inobao.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique = true)
   @Email
   private String email;

   @Column(nullable = false)
   private String password;

   @Column(nullable = false)
   private  String nickname;

   @Column(nullable = false)
   @Enumerated(value = EnumType.STRING)    // Enum값 그대로 db에 저장
   private UserRoleEnum role;

   @Builder
   private User(String email, String password, String nickname,UserRoleEnum role) {
      this.email = email;
      this.password = password;
      this.nickname = nickname;
      this.role=role;
   }
}
