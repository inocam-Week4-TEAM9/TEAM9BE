package com.example.inobao.global.exception;

import com.example.inobao.domain.comment.exception.CommentException;
import com.example.inobao.domain.post.exception.PostException;
import com.example.inobao.domain.user.exception.UserException;
import com.example.inobao.global.responsedto.ApiResponse;
import com.example.inobao.global.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "global exception handler")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ApiResponse<?> handleUserException(UserException e) {
        return ResponseUtils.error(e.getErrorCode());
    }

    @ExceptionHandler(PostException.class)
    public ApiResponse<?> handleUserException(PostException e) {
        return ResponseUtils.error(e.getErrorCode());
    }

    @ExceptionHandler(CommentException.class)
    public ApiResponse<?> handleUserException(CommentException e) {
        return ResponseUtils.error(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(), error.getDefaultMessage()
                ));
        return ResponseUtils.error(HttpStatus.BAD_REQUEST, errors);
    }
}
