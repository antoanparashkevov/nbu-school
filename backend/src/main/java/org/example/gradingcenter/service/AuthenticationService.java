package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.UserLoginDto;
import org.example.gradingcenter.data.dto.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;
import org.example.gradingcenter.data.entity.users.User;

public interface AuthenticationService {
    User registerUser(UserRegisterDto userRegisterDto);

    UserLoginResponseDto loginUser(UserLoginDto userLoginDto);
}
