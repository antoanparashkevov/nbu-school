package org.example.gradingcenter.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public <T> DuplicateEntityException(Class<T> type, String attribute, Object value) {
        throw new EntityNotFoundException(type.getSimpleName(), attribute, value);
    }

    public DuplicateEntityException(String className, String attribute, Object value) {
        super(String.format("%s with %s %s already exists", className, attribute, value));
    }

}