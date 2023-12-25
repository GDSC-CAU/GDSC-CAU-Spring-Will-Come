package cau.gdsc.config.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// ResponseEntity 기반 커스텀 응답
// ResponseEntity 응답 정보 형식을 통일하기 위해 정의
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final ApiHeader header;
    private final T data;

    // 제네릭 메서드로 만들기 위해 <T>를 추가
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(new ApiHeader(ResponseCode.OK.getStatusCode(), ResponseCode.OK.getMessage()), data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(new ApiHeader(ResponseCode.CREATED.getStatusCode(), ResponseCode.CREATED.getMessage()), data);
    }

    public static ApiResponse<Void> noContent() {
        return new ApiResponse<>(new ApiHeader(ResponseCode.NO_CONTENT.getStatusCode(), ResponseCode.NO_CONTENT.getMessage()), null);
    }

    public static <T> ApiResponse<T> created(T data){
        return new ApiResponse<T>(new ApiHeader(ResponseCode.CREATED.getStatusCode(), ResponseCode.CREATED.getMessage()), data);
    }

    public static ApiResponse<Void> noContent(){
        return new ApiResponse<Void>(new ApiHeader(ResponseCode.NO_CONTENT.getStatusCode(), ResponseCode.NO_CONTENT.getMessage()), null);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<>(new ApiHeader(responseCode.getStatusCode(), responseCode.getMessage()), data);
    }
}
