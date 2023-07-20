package com.example.inobao.global.exception;

import com.example.inobao.global.enums.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
