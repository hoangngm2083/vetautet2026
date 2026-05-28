package ngm.hoang.rest.common;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiResponse<T>(
        int status,
        String message,
        Instant timestamp,
        T data,
        Object errors
) {
    public static <T> ApiResponse<T> success(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(
                httpStatus.value(),
                message,
                Instant.now(),
                data,
                null
        );
    }

    public static <T> ApiResponse<T> error(HttpStatus httpStatus, String message, Object errors) {
        return new ApiResponse<>(
                httpStatus.value(),
                message,
                Instant.now(),
                null,
                errors
        );
    }
}
