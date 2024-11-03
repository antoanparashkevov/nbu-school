package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Student;

import java.util.List;

public interface StudentService {
    
    List<Student> getStudents();

    Student getStudent(long id);

    Student createStudent(Student student);

    Student updateStudent(Student student, long id);

    void deleteStudent(long id);
    
}
