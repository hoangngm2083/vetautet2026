package ngm.hoang.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final String model;
    private final String field;
    private final String value;

    public DomainException(Class<?> modelClass, String field, String value, String message) {
        super(message);
        this.field = field;
        this.model = modelClass != null ? modelClass.getSimpleName() : "Model";
        this.value = value;
    }
}