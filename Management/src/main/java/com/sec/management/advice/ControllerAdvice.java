package com.sec.management.advice;

import com.sec.blog.entity.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R argumentException(MethodArgumentNotValidException e) {
        return R.error(e.getFieldError().getField() + e.getFieldError().getDefaultMessage());
    }
}
