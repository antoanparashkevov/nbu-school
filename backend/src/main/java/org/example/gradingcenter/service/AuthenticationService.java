package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.UserLoginDto;
import org.example.gradingcenter.data.dto.users.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;

public interface AuthenticationService {

    UserLoginResponseDto register(UserRegisterDto userRegisterDto);

    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
