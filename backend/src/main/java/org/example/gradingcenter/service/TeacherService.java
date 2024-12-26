package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.users.Teacher;

import java.util.List;

public interface TeacherService {
    
    List<Teacher> getTeachers();

    Teacher getTeacher(long id);

    Teacher createTeacher(Teacher Teacher);

    Teacher updateTeacher(Teacher Teacher, long id);

    void deleteTeacher(long id);
    
}
