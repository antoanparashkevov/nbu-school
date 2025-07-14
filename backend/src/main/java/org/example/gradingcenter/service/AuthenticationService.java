package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.enums.Roles;

public interface AuthenticationService {

    UserLoginResponseDto register(UserRegisterDto userRegisterDto);

    //UserLoginResponseDto login(UserLoginDto userLoginDto);

}
