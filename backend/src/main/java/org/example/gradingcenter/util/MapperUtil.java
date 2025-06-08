package org.example.gradingcenter.util;

import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.BaseEntity;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.web.view.model.ParentViewModel;
import org.example.gradingcenter.web.view.model.SchoolViewModel;
import org.example.gradingcenter.web.view.model.SignupViewModel;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.web.view.model.StudentViewModel;

import java.util.List;
import java.util.Optional;
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
        StudentOutDto studentOut = new StudentOutDto();
        studentOut.setId(student.getId());
        studentOut.setFirstName(student.getFirstName());
        studentOut.setLastName(student.getLastName());
        studentOut.setUsername(student.getUsername());
        studentOut.setAuthorities(student.getAuthorities().stream().map(Role::getAuthorityName).collect(Collectors.toSet()));
        studentOut.setAbsences(student.getAbsences());
        studentOut.setParentIds(student.getParents().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        studentOut.setGrade(entityToDto(student.getGrade()));
        studentOut.setSchool(entityToDto(student.getSchool()));
        return studentOut;
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

    public static SchoolOutDto entityToDto(School school) {
        if (school == null) {
            return null;
        }
        SchoolOutDto schoolDto = new SchoolOutDto();
        schoolDto.setId(school.getId());
        schoolDto.setName(school.getName());
        schoolDto.setAddress(school.getAddress());
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

    public static StudentViewModel dtoToViewModel(StudentOutDto studentOutDto) {
        StudentViewModel studentViewModel = new StudentViewModel();
        studentViewModel.setId(studentOutDto.getId());
        studentViewModel.setFirstName(studentOutDto.getFirstName());
        studentViewModel.setLastName(studentOutDto.getLastName());
        studentViewModel.setAbsences(studentOutDto.getAbsences());
        studentViewModel.setGradeName(Optional.ofNullable(studentOutDto.getGrade()).map(GradeDto::getName).orElse(null));
        studentViewModel.setSchoolId(Optional.ofNullable(studentOutDto.getSchool()).map(SchoolOutDto::getId).orElse(null));
        studentViewModel.setSchoolName(Optional.ofNullable(studentOutDto.getSchool()).map(SchoolOutDto::getName).orElse(null));
        return studentViewModel;
    }

    public static SchoolViewModel dtoToViewModel(SchoolOutDto schoolOutDto) {
        SchoolViewModel schoolViewModel = new SchoolViewModel();
        schoolViewModel.setId(schoolOutDto.getId());
        schoolViewModel.setName(schoolOutDto.getName());
        return schoolViewModel;
    }

    public static ParentViewModel dtoToViewModel(ParentDto parentDto) {
        ParentViewModel parentViewModel = new ParentViewModel();
        parentViewModel.setFirstName(parentDto.getFirstName());
        parentViewModel.setLastName(parentDto.getLastName());
        return parentViewModel;
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
