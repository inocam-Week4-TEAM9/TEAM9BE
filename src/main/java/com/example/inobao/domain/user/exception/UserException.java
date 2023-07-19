package com.example.inobao.domain.user.exception;

import com.example.inobao.global.enums.ErrorCode;
import com.example.inobao.global.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
