package org.example.gradingcenter.util;

import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.web.view.model.SignupViewModel;

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

}
