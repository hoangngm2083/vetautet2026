package ngm.hoang.exception;

public class AlreadyExistsDomainException extends DomainException {

    public AlreadyExistsDomainException(Class<?> modelClass, String field, String value) {
        super(modelClass, field, value, "Already exists in system!");
    }
}
