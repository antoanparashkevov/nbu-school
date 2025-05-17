package org.example.gradingcenter.exceptions;

public class AuthorizationFailureException extends RuntimeException {

    public AuthorizationFailureException(String message) {
        super(message);
    }
}
