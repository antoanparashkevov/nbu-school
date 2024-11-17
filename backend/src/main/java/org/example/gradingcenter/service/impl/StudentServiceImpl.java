package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Student;
import org.example.gradingcenter.data.mappers.StudentMapper;
import org.example.gradingcenter.data.repository.StudentRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class, "id", id));
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student, long id) {
        return this.studentRepository.findById(id)
                .map(student1 -> {
                    studentMapper.mapStudentUpdateDtoToStudent(student, student1);
                    return studentRepository.save(student1);
                }).orElseGet(() ->
                        studentRepository.save(student)
                );
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    
}
