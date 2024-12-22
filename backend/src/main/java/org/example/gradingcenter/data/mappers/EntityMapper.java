package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.UserOutDto;
import org.example.gradingcenter.data.dto.UserRegisterDto;
import org.example.gradingcenter.data.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    private final ModelMapper modelMapper;

    public void mapSchoolUpdateDtoToSchool(final School schoolFrom, School schoolTo) {
        modelMapper.map(schoolFrom, schoolTo);
    }

    public void mapHeadmasterUpdateDtoToHeadmaster(final Headmaster headmasterFrom, Headmaster headmasterTo) {
        modelMapper.map(headmasterFrom, headmasterTo);
    }

    public void mapStudentUpdateDtoToStudent(final Student studentFrom, Student studentTo){
        modelMapper.map(studentFrom, studentTo);
    }

    public void mapParentUpdateDtoToParent(final Parent parentFrom, Parent parentTo){
        modelMapper.map(parentFrom, parentTo);
    }

    public void mapTeacherUpdateDtoToTeacher(final Teacher teacherFrom, Teacher teacherTo){
        modelMapper.map(teacherFrom, teacherTo);
    }

    public void mapGradeUpdateDtoToGrade(final Grade gradeFrom, Grade gradeTo){
        modelMapper.map(gradeFrom, gradeTo);
    }

    public void mapMarkUpdateDtoToMark(final Mark markFrom, Mark markTo){
        modelMapper.map(markFrom, markTo);
    }

    public void mapProgramUpdateDtoToProgram(final Program programFrom, Program programTo){
        modelMapper.map(programFrom, programTo);
    }

    public void mapSubjectUpdateDtoToSubject(final Subject subjectFrom, Subject subjectTo){
        modelMapper.map(subjectFrom, subjectTo);
    }

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

}
