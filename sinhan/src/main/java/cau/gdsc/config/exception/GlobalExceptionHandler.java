package cau.gdsc.config.exception;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.config.api.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

// 전역 예외 처리 핸들러
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class) // 실행 중 이 에러가 발생했을 때 이 함수의 반환값을 전달
    public ResponseEntity<ApiResponse<String>> handleException(BaseException e){
        return ResponseEntity
                .status(e.getResponseCode().getHttpStatus())
                .body(ApiResponse.fail(e.getResponseCode(), e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException e){
        return ResponseEntity
                .status(404)
                .body(ApiResponse.fail(ResponseCode.NOT_FOUND, e.getMessage()));
    }
    
    @ExceptionHandler(AuthException.class) // 인증 관련 에러
    public ResponseEntity<ApiResponse<String>> handleAuthException(AuthException e){
        return ResponseEntity
                .status(e.getResponseCode().getHttpStatus())
                .body(ApiResponse.fail(e.getResponseCode(), e.getMessage()));
    }
}
