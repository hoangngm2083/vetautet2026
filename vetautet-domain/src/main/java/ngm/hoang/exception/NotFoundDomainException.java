package ngm.hoang.exception;

public class NotFoundDomainException extends DomainException {

    public NotFoundDomainException(Class<?> modelClass, String field, String value) {
        super(modelClass, field, value, "Resource not found");
    }
}
