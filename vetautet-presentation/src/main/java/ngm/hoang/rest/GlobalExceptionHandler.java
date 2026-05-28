package ngm.hoang.rest;

import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý lỗi validation từ @Valid, @Validated
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed for one or more fields");
        problemDetail.setTitle("Invalid Request Content");
        problemDetail.setType(URI.create("about:blank")); // Có thể thay bằng URL doc lỗi của dự án

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        problemDetail.setProperty("invalidParams", errors);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    /**
     * Xử lý lỗi logic thông thường (sẽ hữu ích khi chưa định nghĩa Domain Exception riêng)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    /**
     * Xử lý Domain Exception (business rule / invariant).
     */
    @ExceptionHandler(AlreadyExistsDomainException.class)
    public ProblemDetail handleAlreadyExistsDomainException(AlreadyExistsDomainException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict");
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setProperty("model", ex.getModel());
        problemDetail.setProperty("field", ex.getField());
        problemDetail.setProperty("value", ex.getValue());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setProperty("model", ex.getModel());
        problemDetail.setProperty("field", ex.getField());
        problemDetail.setProperty("value", ex.getValue());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    /**
     * Fallback: Xử lý tất cả các lỗi không mong muốn khác
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        // Tránh lộ lỗi stack trace thực sự ra ngoài trong production
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal server error occurred");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setProperty("timestamp", Instant.now());

        // Log lỗi chi tiết ở server
        // log.error("Unhandled exception", ex);

        return problemDetail;
    }
}
