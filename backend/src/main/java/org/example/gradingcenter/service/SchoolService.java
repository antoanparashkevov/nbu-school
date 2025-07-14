package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.entity.School;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SchoolService {

    List<SchoolOutDto> getSchools();

    SchoolOutDto getSchool(long id);

    SchoolOutDto getSchoolByHeadmaster(long headmasterId);

    List<SchoolOutDto> filterSchools(Specification<School> specification);

    SchoolOutDto createSchool(SchoolDto schoolDto);

    SchoolOutDto updateSchool(SchoolDto schoolDto, long id);

    void deleteSchool(long id);
    
}
