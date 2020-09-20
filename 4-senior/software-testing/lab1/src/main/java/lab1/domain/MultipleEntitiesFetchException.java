package lab1.domain;

public class MultipleEntitiesFetchException extends RuntimeException {
    public MultipleEntitiesFetchException() {
        super("Attempted to fetch all entities by passing \"ALL\" enum. For such cases, refer to getters.");
    }

    public MultipleEntitiesFetchException(String message) {
        super(message);
    }
}
