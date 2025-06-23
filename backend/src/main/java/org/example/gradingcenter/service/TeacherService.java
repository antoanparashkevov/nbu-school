package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.EmployeeDto;
import org.example.gradingcenter.data.dto.users.EmployeeInDto;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TeacherService {
    
    List<EmployeeDto> getTeachers();

    EmployeeDto getTeacher(long id);

    List<EmployeeDto> filterTeachers(Specification<Teacher> specification);

    EmployeeDto createTeacher(EmployeeInDto teacher);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TEACHER')")
    EmployeeDto updateTeacher(EmployeeInDto employeeInDto, long id) throws EntityNotFoundException;

    void deleteTeacher(long id);
    
}
