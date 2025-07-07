package org.example.gradingcenter.service.impl;

import lombok.AllArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.*;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.exceptions.AuthorizationFailureException;
import org.example.gradingcenter.service.*;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private ModelMapperConfig mapperConfig;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

   // private TokenService tokenService;

    private UserService userService;

    private ParentService parentService;

    private HeadmasterService headmasterService;

    private StudentService studentService;

    private TeacherService teacherService;

    @Override
    public UserLoginResponseDto register(UserRegisterDto userRegisterDto) {
        User user = MapperUtil.dtoToEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.createUser(user);
        assignRole(savedUser.getId(), userRegisterDto.getRole(), userRegisterDto.getSchoolId(), userRegisterDto.getEgn());

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
            return MapperUtil.entityToDto(loggedInUser);
        }
        throw new AuthorizationFailureException("There is not a logged in user");
    }

    private void assignRole(Long userId, Roles roles, Long schoolId, String egn) {
        switch (roles) {
            case Roles.ROLE_TEACHER -> {
                teacherService.createTeacher(new EmployeeInDto(userId, schoolId));
            }
            case Roles.ROLE_PARENT -> {
                parentService.createParent(userId);
            }
            case Roles.ROLE_HEADMASTER -> {
                headmasterService.createHeadmaster(userId);
            }
            case Roles.ROLE_STUDENT -> {
                studentService.createStudent(new StudentInDto(userId, schoolId, egn));
            }
            case Roles.ROLE_ADMIN -> {
                System.out.println("Admins cannot be systematically created");
            }
        }
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
