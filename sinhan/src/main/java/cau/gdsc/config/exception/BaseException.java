package cau.gdsc.config.exception;

import cau.gdsc.config.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 기본 예외 처리
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private final ResponseCode responseCode;

    @Override
    public String getMessage() {
        return responseCode.getMessage();
    }
}
