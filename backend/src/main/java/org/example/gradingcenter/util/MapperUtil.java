package org.example.gradingcenter.util;

import org.example.gradingcenter.data.dto.*;
import org.example.gradingcenter.data.dto.users.*;
import org.example.gradingcenter.data.entity.BaseEntity;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.example.gradingcenter.data.entity.users.*;
import org.example.gradingcenter.web.view.model.*;
import org.example.gradingcenter.data.entity.Role;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class MapperUtil {

    public static <D, V> List<V> mapList(List<D> source, Function<? super D, ? extends V> mapper) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static List<StudentOutDto> entityToDtoAsList(List<Student> students) {
        return students.stream().map(MapperUtil::entityToDto).collect(Collectors.toList());
    }

    public static SchoolDto viewModelToDto(SchoolViewModel schoolViewModel) {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName(schoolViewModel.getName());
        schoolDto.setAddress(schoolViewModel.getAddress());
        schoolDto.setHeadmasterId(schoolViewModel.getHeadmasterId());
        return schoolDto;
    }

    public static UserRegisterDto viewModelToDto(SignupViewModel signupViewModel) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setFirstName(signupViewModel.getFirstName());
        userRegisterDto.setLastName(signupViewModel.getLastName());
        userRegisterDto.setUsername(signupViewModel.getUsername());
        userRegisterDto.setPassword(signupViewModel.getPassword());
        userRegisterDto.setConfirmPassword(signupViewModel.getConfirmPassword());
        userRegisterDto.setRoles(signupViewModel.getRoles()
                .stream()
                .map(Roles::valueOf)
                .collect(Collectors.toSet()));
        userRegisterDto.setSchoolId(signupViewModel.getSchoolId());
        userRegisterDto.setEgn(signupViewModel.getEgn());
        return userRegisterDto;
    }

    public static MarkDto viewModelToDto(MarkViewModel markViewModel) {
        MarkDto markDto = new MarkDto();
        markDto.setMark(markViewModel.getMark());
        markDto.setSubjectName(markViewModel.getSubjectName());
        markDto.setTeacherId(markViewModel.getTeacherId());
        return markDto;
    }

    public static StudentInDto viewModelToDto(StudentViewModel studentViewModel) {
        StudentInDto studentInDto = new StudentInDto();
        studentInDto.setEgn(studentViewModel.getEgn());
        studentInDto.setFirstName(studentViewModel.getFirstName());
        studentInDto.setLastName(studentViewModel.getLastName());
        studentInDto.setAbsences(studentViewModel.getAbsences());
        studentInDto.setGradeName(studentViewModel.getGradeName());
        studentInDto.setSchoolId(studentViewModel.getSchoolId());
        return studentInDto;
    }

    public static ParentDto viewModelToDto(ParentViewModel parentViewModel) {
        ParentDto parentDto = new ParentDto();
        parentDto.setFirstName(parentViewModel.getFirstName());
        parentDto.setLastName(parentViewModel.getLastName());
        return parentDto;
    }

    public static EmployeeInDto viewModelToDto(EmployeeViewModel employeeViewModel) {
        EmployeeInDto employeeInDto = new EmployeeInDto();
        employeeInDto.setFirstName(employeeViewModel.getFirstName());
        employeeInDto.setLastName(employeeViewModel.getLastName());
        employeeInDto.setSchoolId(employeeViewModel.getSchoolId());
        return employeeInDto;
    }

    public static SubjectDto viewModelToDto(SubjectViewModel subjectViewModel) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName(SubjectName.valueOf(subjectViewModel.getName().toUpperCase()));
        subjectDto.setGradeName(subjectViewModel.getGradeName());
        return subjectDto;
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

    public static ParentDto entityToDto(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setFirstName(parent.getFirstName());
        parentDto.setLastName(parent.getLastName());
        parentDto.setUsername(parent.getUsername());
        parentDto.setAuthorities(parent.getAuthorities().stream().map(Role::getAuthorityName).collect(Collectors.toSet()));
        return parentDto;
    }

    public static StudentOutDto entityToDto(Student student) {
        StudentOutDto studentOut = new StudentOutDto();
        studentOut.setId(student.getId());
        studentOut.setEgn(student.getEgn());
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

    public static EmployeeDto entityToDto(Teacher teacher) {
        return employeeToDto(teacher);
    }

    public static EmployeeDto entityToDto(Headmaster headmaster) {
        return employeeToDto(headmaster);
    }

    private static <E extends Employee> EmployeeDto employeeToDto(E employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setUsername(employee.getUsername());
        employeeDto.setAuthorities(employee.getAuthorities().stream().map(Role::getAuthorityName).collect(Collectors.toSet()));
        employeeDto.setSchool(entityToDto(employee.getSchool()));
        return employeeDto;
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
        if (school.getHeadmaster() != null) {
            schoolDto.setHeadmasterId(school.getHeadmaster().getId());
            schoolDto.setHeadmasterFirstName(school.getHeadmaster().getFirstName());
            schoolDto.setHeadmasterLastName(school.getHeadmaster().getLastName());
        }
        return schoolDto;
    }

    public static StudentViewModel dtoToViewModel(StudentOutDto studentOutDto) {
        StudentViewModel studentViewModel = new StudentViewModel();
        studentViewModel.setId(studentOutDto.getId());
        studentViewModel.setEgn(studentOutDto.getEgn());
        studentViewModel.setFirstName(studentOutDto.getFirstName());
        studentViewModel.setLastName(studentOutDto.getLastName());
        studentViewModel.setAbsences(studentOutDto.getAbsences());
        studentViewModel.setParentIds(studentOutDto.getParentIds());
        studentViewModel.setGradeName(Optional.ofNullable(studentOutDto.getGrade()).map(GradeDto::getName).orElse(null));
        studentViewModel.setSchoolId(Optional.ofNullable(studentOutDto.getSchool()).map(SchoolOutDto::getId).orElse(null));
        studentViewModel.setSchoolName(Optional.ofNullable(studentOutDto.getSchool()).map(SchoolOutDto::getName).orElse(null));
        return studentViewModel;
    }

    public static SchoolViewModel dtoToViewModel(SchoolOutDto schoolOutDto) {
        SchoolViewModel schoolViewModel = new SchoolViewModel();
        schoolViewModel.setId(schoolOutDto.getId());
        schoolViewModel.setName(schoolOutDto.getName());
        schoolViewModel.setAddress(schoolOutDto.getAddress());
        schoolViewModel.setHeadmasterFirstName(schoolOutDto.getHeadmasterFirstName());
        schoolViewModel.setHeadmasterLastName(schoolOutDto.getHeadmasterLastName());
        return schoolViewModel;
    }

    public static ParentViewModel dtoToViewModel(ParentDto parentDto) {
        ParentViewModel parentViewModel = new ParentViewModel();
        parentViewModel.setId(parentDto.getId());
        parentViewModel.setFirstName(parentDto.getFirstName());
        parentViewModel.setLastName(parentDto.getLastName());
        parentViewModel.setNumberOfChildren(Optional.ofNullable(parentDto.getChildrenIds()).map(Collection::size).orElse(0));
        return parentViewModel;
    }

    public static EmployeeViewModel dtoToViewModel(EmployeeDto employeeDto) {
        EmployeeViewModel employeeViewModel = new EmployeeViewModel();
        employeeViewModel.setId(employeeDto.getId());
        employeeViewModel.setFirstName(employeeDto.getFirstName());
        employeeViewModel.setLastName(employeeDto.getLastName());
        employeeViewModel.setSchoolId(employeeDto.getSchoolId());
        employeeViewModel.setSchoolName(employeeDto.getSchoolName());
        return employeeViewModel;
    }

    public static SubjectViewModel dtoToViewModel(SubjectOutDto subjectOutDto) {
        SubjectViewModel subjectViewModel = new SubjectViewModel();
        subjectViewModel.setId(subjectOutDto.getId());
        subjectViewModel.setName(subjectOutDto.getName().toString());
        subjectViewModel.setGradeName(subjectOutDto.getGradeName());
        return subjectViewModel;
    }

    public static MarkViewModel dtoToViewModel(MarkOutDto markOutDto, List<EmployeeDto> employeeDtoList) {
        Map<Long, EmployeeDto> idToTeacherMap = employeeDtoList.stream().collect(toMap(EmployeeDto::getId, Function.identity()));
        MarkViewModel markViewModel = new MarkViewModel();
        markViewModel.setId(markOutDto.getId());
        markViewModel.setMark(markOutDto.getMark());
        markViewModel.setTeacherId(markOutDto.getTeacherId());
        markViewModel.setTeacherName(idToTeacherMap.get(markOutDto.getTeacherId()).getFirstName() + " " + idToTeacherMap.get(markOutDto.getTeacherId()).getLastName());
        markViewModel.setSubjectName(markOutDto.getSubjectName());
        return markViewModel;
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

    public static School dtoToEntity(SchoolDto schoolDto) {
        School school = new School();
        school.setName(schoolDto.getName());
        school.setAddress(schoolDto.getAddress());
        return school;
    }

    public static void dtoToEntity(SchoolDto schoolDto, School school) {
        school.setName(schoolDto.getName());
        school.setAddress(schoolDto.getAddress());
    }

}
