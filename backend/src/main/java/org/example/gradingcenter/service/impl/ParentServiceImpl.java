package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.ParentRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ParentService;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ParentRepository parentRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<ParentDto> getParents() {
        return mapperConfig.mapList(parentRepository.findAll(), ParentDto.class);
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public ParentDto createParent(Long userId) {
        User user = userService.fetchUser(userId);
        Role userRole = roleService.fetchRole(Roles.PARENT);
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
    public void deleteParent(long id) {
        parentRepository.deleteById(id);
    }
    
}
