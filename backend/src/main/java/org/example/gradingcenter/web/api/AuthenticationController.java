package org.example.gradingcenter.web.api;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.UserLoginDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;
import org.example.gradingcenter.data.entity.User;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.exceptions.AuthenticationFailureException;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.UserService;
import org.example.gradingcenter.util.AuthenticationHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticationController {


    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final EntityMapper userMapper;
    public static final String LOGGED_USER_KEY = "currentUser";
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto,
                                   BindingResult errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            User loggedUser = authenticationHelper.verifyAuthentication(userLoginDto.getUsername(), userLoginDto.getPassword());
            session.setAttribute(LOGGED_USER_KEY, loggedUser);
            return ResponseEntity.ok().body(userMapper.mapUserToUserOutDto(loggedUser));
        } catch (AuthenticationFailureException e) {
            errors.rejectValue("username", "auth.error", e.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session.getAttribute(LOGGED_USER_KEY) == null){
            return ResponseEntity.notFound().build();
        }
        session.removeAttribute(LOGGED_USER_KEY);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto register,
                                      BindingResult errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            if (!register.getPassword().equals(register.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password_error",
                        "Password confirmation should match password.");
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            User user = userMapper.mapUserRegisterDtoToUser(register);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userService.createUser(user);
            session.setAttribute(LOGGED_USER_KEY, newUser);
            return ResponseEntity.ok().body(userMapper.mapUserToUserOutDto(newUser));
        } catch (DuplicateEntityException | EntityNotFoundException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

}

