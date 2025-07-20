package org.example.gradingcenter.configuration;

import org.example.gradingcenter.exceptions.AuthorizationFailureException;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionConfig {

//    @ExceptionHandler(Exception.class)
//    protected String handleException(Exception exception, Model model) {
//        model.addAttribute("message", exception.getMessage());
//        return "/error";
//    }

    @ExceptionHandler({EntityNotFoundException.class})
    public String handleException(EntityNotFoundException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("status", HttpStatus.NOT_FOUND);
        return "/error";
    }

    @ExceptionHandler({DuplicateEntityException.class})
    public String handleException(DuplicateEntityException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("status", HttpStatus.CONFLICT);
        return "/error";
    }

    @ExceptionHandler({AuthorizationFailureException.class})
    public String handleException(AuthorizationFailureException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("status", HttpStatus.UNAUTHORIZED);
        return "/error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(
            AccessDeniedException ex, Model model) {
        // Log the exception
        // You can use any logging framework here like slf4j
        System.err.println("Access Denied: " + ex.getMessage());

        // Construct an error response
        String errorMessage = "Access Denied: You do not have the necessary permissions.";
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", HttpStatus.UNAUTHORIZED);
        return "/error";
    }

}
