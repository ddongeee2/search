package io.github.ddongeee.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class BlogControllerAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResultResponse<Void> handleUnknownException(final Exception e) {
        String errorId = UUID.randomUUID().toString();
        log.error("[{}] Unknown Exception. message={}", errorId, e.getMessage(), e);
        return ResultResponse.fail(String.format("[%s] 알 수 없는 오류가 발생하였습니다. msg: %s", errorId, e.getMessage()));
    }
}
