package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.StudentInDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.GradeRepository;
import org.example.gradingcenter.data.repository.StudentRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ParentService;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.StudentService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    private final StudentRepository studentRepository;

    private final RoleService roleService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final ParentService parentService;

    private final GradeRepository gradeRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<StudentOutDto> getStudents() {
        return mapperConfig.mapList(studentRepository.findAll(), StudentOutDto.class);
    }

    @Override
    public StudentOutDto getStudent(long id) {
        return mapperConfig.getModelMapper().map(fetchStudent(id), StudentOutDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public StudentOutDto createStudent(StudentInDto student) {
        Optional<Student> existingStudent = studentRepository.findById(student.getUserId());
        if (existingStudent.isPresent()) {
            throw new DuplicateEntityException(Student.class, "id", student.getUserId());
        }

        User user = userService.fetchUser(student.getUserId());
        Role userRole = roleService.fetchRole(Roles.STUDENT);
        user.getAuthorities().add(userRole);
        userRepository.save(user);
        entityManager.createNativeQuery(
                        " INSERT INTO student (id) VALUES (:userId) ")
                .setParameter("userId", user.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();

        Student newStudent = fetchStudent(student.getUserId());
        return modifyUser(student, newStudent);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TEACHER')")
    public StudentOutDto updateStudent(StudentInDto student, long id) {
        return this.studentRepository.findById(id)
                .map(studentToUpdate -> modifyUser(student, studentToUpdate))
                .orElseThrow(() -> new EntityNotFoundException(Student.class, "id", id));
    }

    private StudentOutDto modifyUser(StudentInDto student, Student studentToUpdate) {
        studentToUpdate.setAbsences(student.getAbsences());
        setParents(studentToUpdate, student.getParentIds());
        studentToUpdate.setGrade(fetchGrade(student.getGradeName(), student.getSchoolName()));
        return mapperConfig.getModelMapper().map(studentRepository.save(studentToUpdate), StudentOutDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    private Student fetchStudent(long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class, "id", id));
    }

    private Grade fetchGrade(String name, String schoolName) {
        return gradeRepository
                .findByNameAndSchool_Name(name, schoolName)
                .orElseThrow(() -> new EntityNotFoundException(Grade.class,
                                                               "name and school",
                                                                name + " and " + schoolName));
    }

    private void setParents(Student student, List<Long> parentIds) {
        if (parentIds == null || parentIds.isEmpty()) {
            return;
        }
        List<Parent> parents = new ArrayList<>();
        for (Long parentId : parentIds) {
            parents.add(parentService.fetchParent(parentId));
        }
        student.setParents(parents);
    }

}
