package com.example.inobao.domain.post.exception;

import com.example.inobao.global.enums.ErrorCode;
import com.example.inobao.global.exception.GlobalException;

public class PostException extends GlobalException {
    public PostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
