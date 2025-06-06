package org.example.gradingcenter.util;

import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.BaseEntity;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.web.view.model.SchoolViewModel;
import org.example.gradingcenter.web.view.model.SignupViewModel;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.web.view.model.StudentViewModel;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
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

    public static List<StudentOutDto> entityToDtoAsList(List<Student> students) {
        return students.stream().map(MapperUtil::entityToDto).collect(Collectors.toList());
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

    public static StudentOutDto entityToDto(Student student) {
        StudentOutDto userOutDto = new StudentOutDto();
        userOutDto.setId(student.getId());
        userOutDto.setFirstName(student.getFirstName());
        userOutDto.setLastName(student.getLastName());
        userOutDto.setUsername(student.getUsername());
        userOutDto.setAuthorities(student.getAuthorities().stream().map(Role::getAuthorityName).collect(Collectors.toSet()));
        userOutDto.setAbsences(student.getAbsences());
        userOutDto.setParentIds(student.getParents().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        userOutDto.setGrade(entityToDto(student.getGrade()));
        userOutDto.setSchool(entityToDto(student.getSchool()));
        return userOutDto;
    }

    public static GradeDto entityToDto(Grade grade) {
        if (grade == null) {
            return null;
        }
        GradeDto gradeDto = new GradeDto();
        gradeDto.setId(grade.getId());
        gradeDto.setName(grade.getName());
        gradeDto.setSchoolId(grade.getSchool().getId());
        return gradeDto;
    }

    public static SchoolDto entityToDto(School school) {
        if (school == null) {
            return null;
        }
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName(school.getName());
        schoolDto.setAddress(school.getName());
        schoolDto.setHeadmasterId(school.getHeadmaster().getId());
        return schoolDto;
    }

    public static List<StudentViewModel> dtoToViewModelAsList(List<StudentOutDto> studentOutDtoList) {
        return studentOutDtoList.stream().map(MapperUtil::dtoToViewModel).collect(Collectors.toList());
    }

    public static <D, V> List<V> mapList(List<D> source,
        Function<? super D, ? extends V> mapper) {
        return source.stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    public static StudentViewModel dtoToViewModel(StudentOutDto studentOutDto){
        StudentViewModel studentViewModel = new StudentViewModel();
        studentViewModel.setId(studentOutDto.getId());
        studentViewModel.setFirstName(studentOutDto.getFirstName());
        studentViewModel.setLastName(studentOutDto.getLastName());
        studentViewModel.setParentIds(studentOutDto.getParentIds());
        studentViewModel.setAbsences(studentOutDto.getAbsences());
        studentViewModel.setGrade(studentOutDto.getGrade());
        studentViewModel.setSchool(studentOutDto.getSchool());
        return studentViewModel;
    }

    public static SchoolViewModel dtoToViewModel(SchoolOutDto schoolOutDto){
        SchoolViewModel schoolViewModel = new SchoolViewModel();
        schoolViewModel.setId(schoolOutDto.getId());
        schoolViewModel.setName(schoolOutDto.getName());
        return schoolViewModel;
    }

    public static User dtoToEntity(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setFirstName(userRegisterDto.getFirstName());
        user.setLastName(userRegisterDto.getLastName());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        user.setAccountNonExpired(userRegisterDto.isAccountNonExpired());
        user.setAccountNonLocked(userRegisterDto.isAccountNonLocked());
        user.setCredentialsNonExpired(userRegisterDto.isCredentialsNonExpired());
        user.setEnabled(userRegisterDto.isEnabled());
        return user;
    }

}
