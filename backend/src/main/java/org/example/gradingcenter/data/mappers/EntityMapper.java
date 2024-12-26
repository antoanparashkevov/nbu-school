package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
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

}
