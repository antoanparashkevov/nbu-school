package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.TeacherDto;
import org.example.gradingcenter.data.dto.users.TeacherInDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.TeacherRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.TeacherService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TeacherRepository teacherRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final ModelMapperConfig mapperConfig;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HEADMASTER')")
    public List<TeacherDto> getTeachers() {
        return mapperConfig.mapList(teacherRepository.findAll(), TeacherDto.class);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HEADMASTER')")
    public TeacherDto getTeacher(long id) {
        return mapperConfig.getModelMapper().map(fetchTeacher(id), TeacherDto.class);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HEADMASTER')")
    @Transactional
    public TeacherDto createTeacher(TeacherInDto teacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(teacher.getUserId());
        if (existingTeacher.isPresent()) {
            throw new DuplicateEntityException(Teacher.class, "id", teacher.getUserId());
        }

        User user = userService.fetchUser(teacher.getUserId());
        Role userRole = roleService.fetchRole(Roles.ROLE_TEACHER);
        user.getAuthorities().add(userRole);
        userRepository.save(user);
        entityManager.createNativeQuery(
                        " INSERT INTO teacher (id, school_id) VALUES (:userId, :schoolId) ")
                .setParameter("userId", teacher.getUserId())
                .setParameter("schoolId", teacher.getSchoolId())
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();

        return mapperConfig.getModelMapper().map(teacherRepository.findById(teacher.getUserId()), TeacherDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }

    private Teacher fetchTeacher(long id) {
        return teacherRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Teacher.class, "id", id));
    }
}
