package org.example.gradingcenter.exceptions;

import java.util.List;

public class EntityNotFoundException extends RuntimeException {

    public <T> EntityNotFoundException(Class<T> type, List<String> attributes, List<Object> values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object value : values) {
            stringBuilder.append(value.toString()).append(", ");
        }
        throw new EntityNotFoundException(type.getSimpleName(), String.join(",", attributes), stringBuilder);
    }

    public <T> EntityNotFoundException(Class<T> type, String attribute, Object value) {
        throw new EntityNotFoundException(type.getSimpleName(), attribute, value);
    }

    public EntityNotFoundException(String className, String attribute, Object value) {
        super(String.format("%s with %s %s was not found!", className, attribute, value));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
