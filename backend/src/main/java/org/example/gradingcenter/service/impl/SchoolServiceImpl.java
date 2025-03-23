package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.HeadmasterService;
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

    private final HeadmasterService headmasterService;

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<SchoolOutDto> getSchools() {
        return mapperConfig.mapList(schoolRepository.findAll(), SchoolOutDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolOutDto getSchool(long id) {
        return mapperConfig.getModelMapper().map(fetchSchool(id), SchoolOutDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolOutDto createSchool(SchoolDto schoolDto) {
        validateHeadmaster(schoolDto.getHeadmasterId(), null);
        validateName(schoolDto.getName(), null);
        School school = mapperConfig.getModelMapper().map(schoolDto, School.class);
        school.setHeadmaster(headmasterService.fetchHeadmaster(schoolDto.getHeadmasterId()));
        return mapperConfig.getModelMapper().map(schoolRepository.save(school), SchoolOutDto.class);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolOutDto updateSchool(SchoolDto schoolDto, long id) {
        validateHeadmaster(schoolDto.getHeadmasterId(), id);
        validateName(schoolDto.getName(), id);
        School updatedSchool = fetchSchool(id);
        mapperConfig.getModelMapper().map(schoolDto, updatedSchool);
        updatedSchool.setHeadmaster(headmasterService.fetchHeadmaster(schoolDto.getHeadmasterId()));
        return mapperConfig.getModelMapper().map(schoolRepository.save(updatedSchool), SchoolOutDto.class);
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
        Optional<School> existingSchoolWithSameHeadmaster = schoolRepository.findByHeadmasterId(headmasterId);
        if (existingSchoolWithSameHeadmaster.isPresent() &&
                (id == null || existingSchoolWithSameHeadmaster.get().getId() != id)) {
            throw new DuplicateEntityException("School", "headmaster id", headmasterId.toString());
        }
    }

}
