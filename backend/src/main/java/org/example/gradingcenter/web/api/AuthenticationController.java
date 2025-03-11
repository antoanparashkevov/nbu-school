package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.UserLoginDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;
import org.example.gradingcenter.exceptions.AuthenticationFailureException;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult errors) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            return ResponseEntity.ok().body(authenticationService.login(userLoginDto));
        } catch (AuthenticationFailureException e) {
            errors.rejectValue("username", "auth.error", e.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto register, BindingResult errors) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            if (!register.getPassword().equals(register.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password_error",
                        "Password confirmation should match password.");
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            return ResponseEntity.ok().body(authenticationService.register(register));
        } catch (DuplicateEntityException | EntityNotFoundException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> handleLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

}

