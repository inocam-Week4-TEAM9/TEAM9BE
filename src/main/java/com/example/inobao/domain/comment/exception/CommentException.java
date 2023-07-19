package com.example.inobao.domain.comment.exception;

import com.example.inobao.global.enums.ErrorCode;
import com.example.inobao.global.exception.GlobalException;

public class CommentException extends GlobalException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
