package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.gradingcenter.util.MapperUtil.dtoToEntity;
import static org.example.gradingcenter.util.MapperUtil.entityToDto;
import static org.example.gradingcenter.util.MapperUtil.mapList;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    private final HeadmasterService headmasterService;

    @Override
    public List<SchoolOutDto> getSchools() {
        return mapList(schoolRepository.findAll(), MapperUtil::entityToDto);
    }

    @Override
    public SchoolOutDto getSchool(long id) {
        return entityToDto(fetchSchool(id));
    }

    @Override
    public SchoolOutDto getSchoolByHeadmaster(long headmasterId) {
        return entityToDto(schoolRepository.findByHeadmasterId(headmasterId).orElseThrow(() -> new EntityNotFoundException(School.class, "headmaster Id", headmasterId)));
    }

    @Override
    public List<SchoolOutDto> filterSchools(Specification<School> specification) {
        return mapList(schoolRepository.findAll(specification), MapperUtil::entityToDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolOutDto createSchool(SchoolDto schoolDto) {
        validateHeadmaster(schoolDto.getHeadmasterId(), null);
        validateName(schoolDto.getName(), null);
        School school = dtoToEntity(schoolDto);
        school.setHeadmaster(headmasterService.fetchHeadmaster(schoolDto.getHeadmasterId()));
        return entityToDto(schoolRepository.save(school));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SchoolOutDto updateSchool(SchoolDto schoolDto, long id) {
        validateHeadmaster(schoolDto.getHeadmasterId(), id);
        validateName(schoolDto.getName(), id);
        School updatedSchool = fetchSchool(id);
        dtoToEntity(schoolDto, updatedSchool);
        updatedSchool.setHeadmaster(headmasterService.fetchHeadmaster(schoolDto.getHeadmasterId()));
        return entityToDto(schoolRepository.save(updatedSchool));
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
