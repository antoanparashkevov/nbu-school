package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Headmaster;
import org.example.gradingcenter.data.entity.Parent;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    private final ModelMapper modelMapper;

    public void mapSchoolUpdateDtoToSchool(School schoolFrom, School schoolTo) {
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

}
