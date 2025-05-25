package org.example.gradingcenter.util;

import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.web.view.model.SignupViewModel;
import org.example.gradingcenter.data.entity.Role;

import java.util.stream.Collectors;

public class MapperUtil {

    public static UserRegisterDto viewModelToDto(SignupViewModel signupViewModel) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setFirstName(signupViewModel.getFirstName());
        userRegisterDto.setLastName(signupViewModel.getLastName());
        userRegisterDto.setUsername(signupViewModel.getUsername());
        userRegisterDto.setPassword(signupViewModel.getPassword());
        userRegisterDto.setConfirmPassword(signupViewModel.getConfirmPassword());
        userRegisterDto.setRole(Roles.valueOf(signupViewModel.getRole()));
        userRegisterDto.setSchoolId(signupViewModel.getSchoolId());
        return userRegisterDto;
    }

    public static UserOutDto entityToDto(User user) {
        UserOutDto userOutDto = new UserOutDto();
        userOutDto.setId(user.getId());
        userOutDto.setFirstName(user.getFirstName());
        userOutDto.setLastName(user.getLastName());
        userOutDto.setUsername(user.getUsername());
        userOutDto.setAuthorities(user.getAuthorities().stream().map(Role::getAuthorityName).collect(Collectors.toSet()));
        return userOutDto;
    }

}
