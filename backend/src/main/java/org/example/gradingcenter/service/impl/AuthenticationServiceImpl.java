package org.example.gradingcenter.service.impl;

import lombok.AllArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.RoleRepository;
import org.example.gradingcenter.exceptions.AuthorizationFailureException;
import org.example.gradingcenter.service.AuthenticationService;
import org.example.gradingcenter.service.TokenService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private ModelMapperConfig mapperConfig;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private UserService userService;

    @Override
    public UserLoginResponseDto register(UserRegisterDto userRegisterDto) {
        User user = mapperConfig.getModelMapper().map(userRegisterDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.createUser(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRegisterDto.getUsername(),
                        userRegisterDto.getPassword()));
        //String token = tokenService.generateJwt(auth);
        UserLoginResponseDto loginResponse = mapperConfig.getModelMapper().map(savedUser, UserLoginResponseDto.class);
        //loginResponse.setJwt(token);
        return loginResponse;
    }

    @Override
    public UserOutDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            User loggedInUser = (User) authentication.getPrincipal();

            UserOutDto userOutDto = new UserOutDto();
            userOutDto.setId(loggedInUser.getId());
            userOutDto.setFirstName(loggedInUser.getFirstName());
            userOutDto.setLastName(loggedInUser.getLastName());
            userOutDto.setUsername(loggedInUser.getUsername());
            userOutDto.setAuthorities(loggedInUser.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toSet()));

            return userOutDto;
        }

        throw new AuthorizationFailureException("There is not a logged in user");
    }

//    @Override
//    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
//                        userLoginDto.getPassword()));
//        String token = tokenService.generateJwt(auth);
//        User loggedUser = (User) userService.loadUserByUsername(userLoginDto.getUsername());
//        UserLoginResponseDto loginResponse = mapperConfig.getModelMapper().map(loggedUser, UserLoginResponseDto.class);
//        loginResponse.setJwt(token);
//        return loginResponse;
//    }

}
