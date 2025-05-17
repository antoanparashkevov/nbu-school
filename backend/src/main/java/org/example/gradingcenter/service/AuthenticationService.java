package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;

public interface AuthenticationService {

    UserLoginResponseDto register(UserRegisterDto userRegisterDto);

    UserOutDto getLoggedInUser();

    //UserLoginResponseDto login(UserLoginDto userLoginDto);

}
