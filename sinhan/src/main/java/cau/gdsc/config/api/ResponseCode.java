package cau.gdsc.config.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE) // 열거 상수를 만들기 위함
@Getter
public enum ResponseCode {
    // 열거 상수
    OK(HttpStatus.OK, "OK"),
    CREATED(HttpStatus.CREATED, "CREATED"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "NO_CONTENT"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN");

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode(){
        return httpStatus.value();
    }
}
