package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Teacher;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.TeacherRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final EntityMapper mapper;

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacher(long id) {
        return teacherRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Teacher.class, "id", id));
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher, long id) {
        return this.teacherRepository.findById(id)
                .map(teacher1 -> {
                    mapper.mapTeacherUpdateDtoToTeacher(teacher, teacher1);
                    return this.teacherRepository.save(teacher1);
                }).orElseGet(() ->
                        this.teacherRepository.save(teacher)
                );
    }

    @Override
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }
    
}
