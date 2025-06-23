package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.TeacherDto;
import org.example.gradingcenter.data.dto.users.TeacherInDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.data.repository.TeacherRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.TeacherService;
import org.example.gradingcenter.service.UserService;
import org.example.gradingcenter.util.DataUtil;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.example.gradingcenter.util.DataUtil.fetchObjectFromDb;
import static org.example.gradingcenter.util.MapperUtil.entityToDto;
import static org.example.gradingcenter.util.MapperUtil.mapList;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TeacherRepository teacherRepository;

    private final SchoolRepository schoolRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HEADMASTER')")
    public List<TeacherDto> getTeachers() {
        return mapList(teacherRepository.findAll(), MapperUtil::entityToDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HEADMASTER')")
    public TeacherDto getTeacher(long id) {
        return entityToDto(fetchObjectFromDb(teacherRepository, id, Teacher.class));
    }

    @Override
    public List<TeacherDto> filterTeachers(Specification<Teacher> specification) {
        return mapList(teacherRepository.findAll(specification), MapperUtil::entityToDto);
    }

    @Override
    @Transactional
    public TeacherDto createTeacher(TeacherInDto teacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(teacher.getUserId());
        if (existingTeacher.isPresent()) {
            throw new DuplicateEntityException(Teacher.class, "id", teacher.getUserId());
        }

        User user = userService.fetchUser(teacher.getUserId());
        Role userRole = roleService.fetchRole(Roles.ROLE_TEACHER);
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }
        user.getAuthorities().add(userRole);
        userRepository.save(user);
        entityManager.createNativeQuery(
                        " INSERT INTO teacher (id, school_id) VALUES (:userId, :schoolId) ")
                .setParameter("userId", teacher.getUserId())
                .setParameter("schoolId", teacher.getSchoolId())
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();
        return entityToDto(fetchObjectFromDb(teacherRepository, teacher.getUserId(), Teacher.class));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TEACHER')")
    public TeacherDto updateTeacher(TeacherInDto teacherInDto, long id) throws EntityNotFoundException {
        Teacher teacher = this.teacherRepository.findById(id)
                .map((studentToUpdate) -> {
                    studentToUpdate.setFirstName(teacherInDto.getFirstName());
                    studentToUpdate.setLastName(teacherInDto.getLastName());
                    if (teacherInDto.getSchoolId() != null) {
                        studentToUpdate.setSchool(fetchObjectFromDb(schoolRepository, teacherInDto.getSchoolId(), School.class));
                    }
                    return teacherRepository.save(studentToUpdate);
                })
                .orElseThrow(() -> new EntityNotFoundException(Teacher.class, "id", id));
        return entityToDto(teacher);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }

}
