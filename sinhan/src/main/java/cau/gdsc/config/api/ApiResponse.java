package cau.gdsc.config.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private static final int SUCCESS_CODE = 200;

    private final ApiHeader header;
    private final T data;

    // 제네릭 메서드로 만들기 위해 <T>를 추가
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(new ApiHeader(ResponseCode.OK.getStatusCode(), ResponseCode.OK.getMessage()), data);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getStatusCode(), responseCode.getMessage()), data);
    }
}
