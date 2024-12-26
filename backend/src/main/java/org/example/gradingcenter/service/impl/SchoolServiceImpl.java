package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    private final EntityMapper entityMapper;

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
    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public School updateSchool(School School, long id) {
        return this.schoolRepository.findById(id)
                .map(School1 -> {
                    entityMapper.mapSchoolUpdateDtoToSchool(School, School1);
                    return schoolRepository.save(School1);
                }).orElseGet(() ->
                        schoolRepository.save(School)
                );
    }

    @Override
    public void deleteSchool(long id) {
        schoolRepository.deleteById(id);
    }
}
