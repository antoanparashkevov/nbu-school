package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.ParentRepository;
import org.example.gradingcenter.data.repository.StudentRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.AuthenticationFailureException;
import org.example.gradingcenter.exceptions.AuthorizationFailureException;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.*;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.example.gradingcenter.util.MapperUtil.entityToDto;
import static org.example.gradingcenter.util.MapperUtil.mapList;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    @PersistenceContext
    private EntityManager entityManager;
    private final ParentRepository parentRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final StudentRepository studentRepository;
    private final ModelMapperConfig mapperConfig;
    private final AuthorizationService authService;

    @Override
    public List<ParentDto> getParents() {
        return mapperConfig.mapList(parentRepository.findAll(), ParentDto.class);
    }

    @Override
    public List<ParentDto> getParents(List<Long> parentIds) {
        return mapperConfig.mapList(parentRepository.findAllById(parentIds), ParentDto.class);
    }

    @Override
    public List<ParentDto> getParents(Long childId) {
        return mapperConfig.mapList(parentRepository.findAllByChildrenId(childId), ParentDto.class);
    }

    @Override
    public List<ParentDto> filterParents(Specification<Parent> specification) {
        return mapList(parentRepository.findAll(specification), MapperUtil::entityToDto);
    }

    @Override
    public ParentDto getParent(long id) {
        return mapperConfig.getModelMapper().map(fetchParent(id), ParentDto.class);
    }

    @Override
    public Parent fetchParent(long id) {
        return parentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parent.class, "id", id));
    }

    @Override
    @Transactional
    public ParentDto createParent(Long userId) {
        Optional<Parent> existingParent = parentRepository.findById(userId);
        if (existingParent.isPresent()) {
            throw new DuplicateEntityException(Parent.class, "id", userId);
        }

        User user = userService.fetchUser(userId);
        Role userRole = roleService.fetchRole(Roles.ROLE_PARENT);
        if (user.getAuthorities() == null){
            user.setAuthorities(new HashSet<>());
        }
        user.getAuthorities().add(userRole);
        userRepository.save(user);
        entityManager.createNativeQuery(
                        " INSERT INTO parent (id) VALUES (:userId) ")
                .setParameter("userId", user.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();
        return mapperConfig.getModelMapper().map(fetchParent(userId), ParentDto.class);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PARENT')")
    public ParentDto updateParent(ParentDto parentDto, long id) throws EntityNotFoundException {
        if (!authService.hasAnyRole(Roles.ROLE_HEADMASTER, Roles.ROLE_ADMIN) && authService.getLoggedInUser().getId() != id) {
            throw new AuthorizationFailureException(Parent.class, "update");
        }
        Parent parent = this.parentRepository.findById(id)
                .map((parentToUpdate) -> {
                    parentToUpdate.setFirstName(parentDto.getFirstName());
                    parentToUpdate.setLastName(parentDto.getLastName());
                    return parentRepository.save(parentToUpdate);
                })
                .orElseThrow(() -> new EntityNotFoundException(Parent.class, "id", id));
        return entityToDto(parent);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PARENT')")
    public void addChildToParent(Long parentId, String childEgn) {
        if (!authService.hasAnyRole(Roles.ROLE_ADMIN) && authService.getLoggedInUser().getId() != parentId) {
            throw new AuthorizationFailureException(Parent.class, "update");
        }
        Parent parent = fetchParent(parentId);
        Student student = studentRepository
                .findByEgn(childEgn.trim())
                .orElseThrow(() -> new EntityNotFoundException(Student.class, "egn", childEgn));
        student.getParents().add(parent);
        studentRepository.save(student);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PARENT')")
    public void removeChildFromParent(Long parentId, Long childId) {
        if (!authService.hasAnyRole(Roles.ROLE_ADMIN) && authService.getLoggedInUser().getId() != parentId) {
            throw new AuthorizationFailureException(Parent.class, "update");
        }
        Parent parent = fetchParent(parentId);
        Student student = studentRepository
                .findById(childId)
                .orElseThrow(() -> new EntityNotFoundException(Student.class, "id", childId));
        student.getParents().remove(parent);
        studentRepository.save(student);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PARENT')")
    public void deleteParent(long id) {
        if (authService.getLoggedInUser().getId() != id && !authService.hasAnyRole(Roles.ROLE_ADMIN)) {
            throw new AuthorizationFailureException(Headmaster.class, "delete");
        }
        parentRepository.deleteById(id);
    }
    
}
