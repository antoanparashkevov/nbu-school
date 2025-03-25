package org.example.gradingcenter.exceptions;

import java.util.List;

public class DuplicateEntityException extends RuntimeException {

    public <T> DuplicateEntityException(Class<T> type, List<String> attributes, List<Object> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object value : values) {
            stringBuilder.append(value.toString()).append(", ");
        }
        throw new DuplicateEntityException(type.getSimpleName(), String.join(",", attributes), stringBuilder);
    }

    public <T> DuplicateEntityException(Class<T> type, String attribute, Object value) {
        throw new DuplicateEntityException(type.getSimpleName(), attribute, value);
    }

    public DuplicateEntityException(String className, String attribute, Object value) {
        super(String.format("%s with %s %s already exists", className, attribute, value));
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

}