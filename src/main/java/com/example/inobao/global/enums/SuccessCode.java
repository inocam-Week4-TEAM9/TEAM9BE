package com.example.inobao.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    /* 200 OK : 요청이 성공적으로 완료되었다는 의미입니다. */
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
    POST_UPDATE_SUCCESS(OK, "게시글 수정 성공"),
    POST_DELETE_SUCCESS(OK, "게시글 삭제 성공"),
    COMMENT_UPDATE_SUCCESS(OK, "댓글 수정 완료"),
    COMMENT_DELETE_SUCCESS(OK, "댓글 삭제 성공"),
    LIKE_CANCEL(OK, "좋아요 취소"),
    DISLIKE_CANCEL(OK, "싫어요 취소"),

    /* 201 CREATED : 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성 되었다는 의미입니다. */
    USER_SIGNUP_SUCCESS(CREATED, "회원가입 성공"),
    POST_CREATE_SUCCESS(CREATED, "게시글 작성 성공"),
    COMMENT_CREATE_SUCCESS(CREATED, "댓글 작성 완료"),
    LIKE_SUCCESS(CREATED, "좋아요 성공"),
    DISLIKE_SUCCESS(CREATED, "싫어요 성공");

    private final HttpStatus httpStatus;
    private final String detail;

    SuccessCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
