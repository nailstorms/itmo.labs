package lab1.domain;

public class MultipleEntitiesFetchException extends RuntimeException {
    public MultipleEntitiesFetchException() {
        super("Attempted to fetch all entities with singular entity fetch method by passing \"ALL\" enum. For such cases, refer to getters.");
    }
}
