package cau.gdsc.config.exception;

import cau.gdsc.config.api.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ApiResponse<?> handleException(BaseException e){
        return ApiResponse.fail(e.getResponseCode(), null);
    }
}
