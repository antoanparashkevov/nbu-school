package org.example.gradingcenter.service.impl;

import lombok.AllArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.UserLoginDto;
import org.example.gradingcenter.data.dto.users.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.RoleRepository;
import org.example.gradingcenter.service.AuthenticationService;
import org.example.gradingcenter.service.TokenService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String token = tokenService.generateJwt(auth);
        UserLoginResponseDto loginResponse = mapperConfig.getModelMapper().map(savedUser, UserLoginResponseDto.class);
        loginResponse.setJwt(token);
        return loginResponse;
    }

    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                        userLoginDto.getPassword()));
        String token = tokenService.generateJwt(auth);
        User loggedUser = (User) userService.loadUserByUsername(userLoginDto.getUsername());
        UserLoginResponseDto loginResponse = mapperConfig.getModelMapper().map(loggedUser, UserLoginResponseDto.class);
        loginResponse.setJwt(token);
        return loginResponse;
    }

}
