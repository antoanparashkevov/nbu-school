package org.example.gradingcenter.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DuplicateEntityException extends RuntimeException {

    public <T> DuplicateEntityException(Class<T> type, List<String> attributes, List<Object> values) {

        if (attributes.size() != values.size()) {
            throw new IllegalArgumentException(
                    "attributes and values lists must have the same length");
        }

        String message = IntStream.range(0, attributes.size())
                .mapToObj(i -> attributes.get(i) + "=" + values.get(i))
                .collect(Collectors.joining(", "));

        throw new DuplicateEntityException(type.getSimpleName() + " with " + message + " already exists");
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