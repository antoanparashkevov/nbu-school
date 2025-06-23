package org.example.gradingcenter.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.EmployeeDto;
import org.example.gradingcenter.data.dto.users.EmployeeInDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.HeadmasterRepository;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.exceptions.InvalidBusinessDataException;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.RoleService;
import org.example.gradingcenter.service.UserService;
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
public class HeadmasterServiceImpl implements HeadmasterService {

    private final HeadmasterRepository headmasterRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final SchoolRepository schoolRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<EmployeeDto> getHeadmasters() {
        return mapList(headmasterRepository.findAll(), MapperUtil::entityToDto);
    }

    @Override
    public List<EmployeeDto> filterHeadmasters(Specification<Headmaster> specification) {
        return mapList(headmasterRepository.findAll(specification), MapperUtil::entityToDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EmployeeDto getHeadmaster(long id) {
        return entityToDto(fetchHeadmaster(id));
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EmployeeDto createHeadmaster(Long userId) {
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
        return entityToDto(fetchHeadmaster(userId));
    }

    @Override
    public void updateHeadmaster(EmployeeInDto headmasterInDto, long id) throws EntityNotFoundException {
        Headmaster headmaster = this.headmasterRepository.findById(id)
                .map((headmasterToUpdate) -> {
                    headmasterToUpdate.setFirstName(headmasterInDto.getFirstName());
                    headmasterToUpdate.setLastName(headmasterInDto.getLastName());
                    if (headmasterInDto.getSchoolId() != null) {
                        School school = fetchObjectFromDb(schoolRepository, headmasterInDto.getSchoolId(), School.class);
                        if (school.getHeadmaster() != null && school.getHeadmaster().getId() != headmasterToUpdate.getId()) {
                            throw new InvalidBusinessDataException("School " + school.getName() + " already has a headmaster");
                        }
                        headmasterToUpdate.setSchool(fetchObjectFromDb(schoolRepository, headmasterInDto.getSchoolId(), School.class));
                    }
                    return headmasterRepository.save(headmasterToUpdate);
                })
                .orElseThrow(() -> new EntityNotFoundException(Headmaster.class, "id", id));
        entityToDto(headmaster);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteHeadmaster(long id) {
        headmasterRepository.deleteById(id);
    }

}
