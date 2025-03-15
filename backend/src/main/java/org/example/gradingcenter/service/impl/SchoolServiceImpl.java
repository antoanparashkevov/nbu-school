package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<SchoolDto> getSchools() {
        return mapperConfig.mapList(schoolRepository.findAll(), SchoolDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolDto getSchool(long id) {
        return mapperConfig.getModelMapper().map(fetchSchool(id), SchoolDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolDto createSchool(SchoolDto schoolDto) {
        validateHeadmaster(schoolDto.getHeadmasterId(), null);
        validateName(schoolDto.getName(), null);
        School createdSchool = schoolRepository.save(mapperConfig.getModelMapper().map(schoolDto, School.class));
        return mapperConfig.getModelMapper().map(createdSchool, SchoolDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolDto updateSchool(SchoolDto schoolDto, long id) {
        validateHeadmaster(schoolDto.getHeadmasterId(), id);
        validateName(schoolDto.getName(), id);
        School updatedSchool = this.schoolRepository.findById(id)
                .map(schoolToUpdate -> {
                    mapperConfig.getModelMapper().map(schoolDto, schoolToUpdate);
                    return schoolRepository.save(schoolToUpdate);
                }).orElseGet(() ->
                        schoolRepository.save(mapperConfig.getModelMapper().map(schoolDto, School.class)
                ));
        return mapperConfig.getModelMapper().map(updatedSchool, SchoolDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteSchool(long id) {
        schoolRepository.deleteById(id);
    }

    private School fetchSchool(long id) {
        return schoolRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(School.class, "id", id));
    }

    private void validateName(String name, Long id) {
        Optional<School> existingSchool = schoolRepository.findByName(name);
        if (existingSchool.isPresent() && (id == null || existingSchool.get().getId() != id)) {
            throw new DuplicateEntityException("School", "name", name);
        }
    }

    private void validateHeadmaster(Long headmasterId, Long id) {
        if (headmasterId == null) {
            return;
        }
        Optional<School> existingSchoolWithSameHeadmaster = schoolRepository.findByHeadMasterId(headmasterId);
        if (existingSchoolWithSameHeadmaster.isPresent() &&
                (id == null || existingSchoolWithSameHeadmaster.get().getId() != id)) {
            throw new DuplicateEntityException("School", "headmaster id", headmasterId.toString());
        }
    }

}
