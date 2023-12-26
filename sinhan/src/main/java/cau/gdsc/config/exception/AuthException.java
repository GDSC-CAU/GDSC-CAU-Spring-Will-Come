package cau.gdsc.config.exception;

import cau.gdsc.config.api.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException{
    private final ResponseCode responseCode;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
