package cau.gdsc.config.exception;

import cau.gdsc.config.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<String>> handleException(BaseException e){
        return ResponseEntity
                .status(e.getResponseCode().getHttpStatus())
                .body(ApiResponse.fail(e.getResponseCode(), e.getMessage()));
    }
}
