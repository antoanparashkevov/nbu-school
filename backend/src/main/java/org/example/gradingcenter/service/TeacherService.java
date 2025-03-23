package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.TeacherDto;
import org.example.gradingcenter.data.dto.users.TeacherInDto;

import java.util.List;

public interface TeacherService {
    
    List<TeacherDto> getTeachers();

    TeacherDto getTeacher(long id);

    TeacherDto createTeacher(TeacherInDto teacher);

    void deleteTeacher(long id);
    
}
