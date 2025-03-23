package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.SchoolOutDto;

import java.util.List;

public interface SchoolService {

    List<SchoolOutDto> getSchools();

    SchoolOutDto getSchool(long id);

    SchoolOutDto createSchool(SchoolDto schoolDto);

    SchoolOutDto updateSchool(SchoolDto schoolDto, long id);

    void deleteSchool(long id);
    
}
