package org.example.gradingcenter.exceptions;

public class AuthorizationFailureException extends RuntimeException {

    public <T> AuthorizationFailureException(Class<T> type, String action) {
        super(String.format("You are not authorized to %s this %s", action, type));
    }

}
