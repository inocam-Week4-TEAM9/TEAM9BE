package com.example.inobao.global.responsedto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final int status;
    private final String msg;
    private final T data;
    private final T errors;

    public ApiResponse(boolean success, int status, String msg, T data, T errors) {
        this.success = success;
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.errors = errors;
    }
}
