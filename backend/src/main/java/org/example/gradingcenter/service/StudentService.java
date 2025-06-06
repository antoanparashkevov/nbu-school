package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.StudentInDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.entity.users.Student;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface StudentService {
    
    List<StudentOutDto> getStudents();

    StudentOutDto getStudent(long id);

    List<StudentOutDto> filterStudents(Specification<Student> specification);

    StudentOutDto createStudent(StudentInDto student);

    StudentOutDto updateStudent(StudentInDto student, long id);

    void deleteStudent(long id);
    
}
