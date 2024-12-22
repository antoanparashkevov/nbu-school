package org.example.gradingcenter.util;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.gradingcenter.data.entity.User;
import org.example.gradingcenter.exceptions.AuthenticationFailureException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthenticationHelper {

    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong username or password.";
    public static final String LOGGED_USER_KEY = "currentUser";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Attempts to retrieve the currently logged-in user from the session.
     *
     * @param session the HTTP session
     * @return the currently logged-in user
     * @throws AuthenticationFailureException if no user is logged in
     */
    public User tryGetUser(HttpSession session) {
        User currentUser = (User) session.getAttribute(LOGGED_USER_KEY);
        if (currentUser == null) {
            throw new AuthenticationFailureException("No user logged in.");
        }
        return (User)userService.loadUserByUsername(currentUser.getUsername());
    }

    /**
     * Verifies the authentication of a user based on the provided username and password.
     * Hash the user's password on login if the user existed before the implementation of the passwordEncoder.
     *
     * @param username the username
     * @param password the password
     * @return the authenticated user
     * @throws AuthenticationFailureException if authentication fails due to incorrect username or password
     */
    public User verifyAuthentication(String username, String password) {
        try {
            UserDetails user = userService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
            }
            return (User) user;
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
        }
    }

}
