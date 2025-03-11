package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.entity.School;

import java.util.List;

public interface SchoolService {

    List<School> getSchools();

    School getSchool(long id);

    School createSchool(SchoolDto schoolDto);

    School updateSchool(School school, long id);

    void deleteSchool(long id);
    
}
