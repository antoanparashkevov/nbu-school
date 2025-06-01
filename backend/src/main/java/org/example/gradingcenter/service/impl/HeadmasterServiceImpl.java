package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.HeadmasterDto;
import org.example.gradingcenter.data.dto.users.HeadmasterInDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.HeadmasterRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HeadmasterServiceImpl implements HeadmasterService {

    private final HeadmasterRepository headmasterRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final ModelMapperConfig mapperConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<HeadmasterDto> getHeadmasters() {
        return mapperConfig.mapList(headmasterRepository.findAll(), HeadmasterDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public HeadmasterDto getHeadmaster(long id) {
        return mapperConfig.getModelMapper().map(fetchHeadmaster(id), HeadmasterDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Headmaster fetchHeadmaster(long id) {
        return headmasterRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Headmaster.class, "id", id));
    }

    @Override
    @Transactional
    public HeadmasterDto createHeadmaster(Long userId) {
        Optional<Headmaster> existingHeadmaster = headmasterRepository.findById(userId);
        if (existingHeadmaster.isPresent()) {
            throw new DuplicateEntityException(Headmaster.class, "id", userId);
        }

        User user = userService.fetchUser(userId);
        Role userRole = roleService.fetchRole(Roles.ROLE_HEADMASTER);
        if (user.getAuthorities() == null){
            user.setAuthorities(new HashSet<>());
        }
        user.getAuthorities().add(userRole);
        userRepository.save(user);
        entityManager.createNativeQuery(
                        " INSERT INTO headmaster (id) VALUES (:userId) ")
                .setParameter("userId", user.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();
        return mapperConfig.getModelMapper().map(headmasterRepository.findById(userId), HeadmasterDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteHeadmaster(long id) {
        headmasterRepository.deleteById(id);
    }

}
