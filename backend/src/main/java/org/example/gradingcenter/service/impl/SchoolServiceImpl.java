package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<School> getSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public School getSchool(long id) {
        return schoolRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(School.class, "id", id));
    }

    @Override
    public School createSchool(SchoolDto schoolDto) {
        return schoolRepository.save(mapperConfig.getModelMapper().map(schoolDto, School.class));
    }

    @Override
    public School updateSchool(School school, long id) {
        return this.schoolRepository.findById(id)
                .map(schoolToUpdate -> {
                    mapperConfig.getModelMapper().map(school, schoolToUpdate);
                    return schoolRepository.save(schoolToUpdate);
                }).orElseGet(() ->
                        schoolRepository.save(school)
                );
    }

    @Override
    public void deleteSchool(long id) {
        schoolRepository.deleteById(id);
    }
}
