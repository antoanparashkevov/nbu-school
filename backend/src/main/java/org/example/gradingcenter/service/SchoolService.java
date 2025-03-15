package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SchoolDto;

import java.util.List;

public interface SchoolService {

    List<SchoolDto> getSchools();

    SchoolDto getSchool(long id);

    SchoolDto createSchool(SchoolDto schoolDto);

    SchoolDto updateSchool(SchoolDto schoolDto, long id);

    void deleteSchool(long id);
    
}
