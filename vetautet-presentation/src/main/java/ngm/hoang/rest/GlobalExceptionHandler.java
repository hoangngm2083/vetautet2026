package ngm.hoang.rest;

import ngm.hoang.exception.AlreadyExistsDomainException;
import ngm.hoang.exception.DomainException;
import ngm.hoang.exception.NotFoundDomainException;
import ngm.hoang.rest.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ApiResponse.error(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields",
                errors
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(AlreadyExistsDomainException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleAlreadyExistsDomainException(AlreadyExistsDomainException ex) {
        return ApiResponse.error(HttpStatus.CONFLICT, ex.getMessage(), domainErrorDetails(ex));
    }

    @ExceptionHandler(NotFoundDomainException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFoundDomainException(NotFoundDomainException ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, ex.getMessage(), domainErrorDetails(ex));
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleDomainException(DomainException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), domainErrorDetails(ex));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGenericException(Exception ex) {
        return ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected internal server error occurred",
                null
        );
    }

    private static Map<String, String> domainErrorDetails(DomainException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("model", ex.getModel());
        errors.put("field", ex.getField());
        errors.put("value", ex.getValue());
        return errors;
    }
}
