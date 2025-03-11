package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.UserLoginDto;
import org.example.gradingcenter.data.dto.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;

public interface AuthenticationService {

    UserLoginResponseDto register(UserRegisterDto userRegisterDto);

    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
