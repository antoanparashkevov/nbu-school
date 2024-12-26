package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.UserLoginResponseDto;
import org.example.gradingcenter.data.dto.UserOutDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;
import org.example.gradingcenter.data.entity.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public void mapUserUpdateDtoToUser(final User userFrom, User userTo){
        modelMapper.map(userFrom, userTo);
    }

    public User mapUserRegisterDtoToUser(final UserRegisterDto userFrom){
        User userTo = new User();
        modelMapper.map(userFrom, userTo);
        userTo.setEnabled(true);
        userTo.setAccountNonLocked(true);
        userTo.setAccountNonExpired(true);
        userTo.setCredentialsNonExpired(true);
        return userTo;
    }

    public UserOutDto mapUserToUserOutDto(final User userFrom){
        UserOutDto userTo = new UserOutDto();
        modelMapper.map(userFrom, userTo);
        return userTo;
    }

    public UserLoginResponseDto mapUserToUserLoginResponseDto(final User userFrom){
        UserLoginResponseDto userTo = new UserLoginResponseDto();
        modelMapper.map(userFrom, userTo);
        return userTo;
    }

}
