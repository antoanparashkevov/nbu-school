package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Student;
import org.example.gradingcenter.data.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ModelMapper modelMapper;

    public void mapStudentUpdateDtoToStudent(final Student studentFrom, Student studentTo){
        modelMapper.map(studentFrom, studentTo);
//        mapUserUpdateDtoToUser(studentFrom, studentTo);
//        studentTo.setAbsences(studentFrom.getAbsences());
//        studentTo.setGrade(studentFrom.getGrade());
//        studentTo.setParents(studentFrom.getParents());
    }

}
