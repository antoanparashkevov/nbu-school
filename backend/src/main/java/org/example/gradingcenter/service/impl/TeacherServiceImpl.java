package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.TeacherRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final ModelMapperConfig mapperConfig;

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
                .map(teacherToUpdate -> {
                    mapperConfig.getModelMapper().map(teacher, teacherToUpdate);
                    return this.teacherRepository.save(teacherToUpdate);
                }).orElseGet(() ->
                        this.teacherRepository.save(teacher)
                );
    }

    @Override
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }
    
}
