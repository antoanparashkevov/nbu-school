package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.TeacherDto;
import org.example.gradingcenter.data.dto.users.TeacherInDto;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TeacherService {
    
    List<TeacherDto> getTeachers();

    TeacherDto getTeacher(long id);

    List<TeacherDto> filterTeachers(Specification<Teacher> specification);

    TeacherDto createTeacher(TeacherInDto teacher);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TEACHER')")
    TeacherDto updateTeacher(TeacherInDto teacherInDto, long id) throws EntityNotFoundException;

    void deleteTeacher(long id);
    
}
