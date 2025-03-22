package org.example.gradingcenter.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public <T> EntityNotFoundException(Class<T> type, String attribute, Object value) {
        throw new EntityNotFoundException(type.getSimpleName(), attribute, value);
    }

    public EntityNotFoundException(String className, String attribute, Object value) {
        super(String.format("%s with %s %s was not found!", className, attribute, value));
    }

}
