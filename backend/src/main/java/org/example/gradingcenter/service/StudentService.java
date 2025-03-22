package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.StudentInDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;

import java.util.List;

public interface StudentService {
    
    List<StudentOutDto> getStudents();

    StudentOutDto getStudent(long id);

    StudentOutDto createStudent(StudentInDto student);

    StudentOutDto updateStudent(StudentInDto student, long id);

    void deleteStudent(long id);
    
}
