package org.example.gradingcenter.data.mappers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.GradeUpdateDto;
import org.example.gradingcenter.data.entity.*;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@RequiredArgsConstructor
public class EntityMapper {

    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> getModelMapper().map(element, targetClass))
                .collect(Collectors.toList());
    }

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

    public void mapGradeUpdateDtoToGrade(final GradeUpdateDto gradeFrom, Grade gradeTo){
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
