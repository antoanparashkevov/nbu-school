package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.repository.StudentRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final ModelMapperConfig mapperConfig;

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
                .map(studentToUpdate -> {
                    mapperConfig.getModelMapper().map(student, studentToUpdate);
                    return studentRepository.save(studentToUpdate);
                }).orElseGet(() ->
                        studentRepository.save(student)
                );
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    
}
