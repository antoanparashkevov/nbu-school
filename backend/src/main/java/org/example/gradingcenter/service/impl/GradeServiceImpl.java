package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.GradeCreateDto;
import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.GradeUpdateDto;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.GradeRepository;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    
    private final GradeRepository gradeRepository;

    private final SchoolRepository schoolRepository;

    private final EntityMapper mapper;

    @Override
    public List<GradeDto> getGrades() {
        return this.mapper.mapList(gradeRepository.findAll(), GradeDto.class);
    }

    @Override
    public GradeDto getGrade(long id) {
        return mapper.getModelMapper().map(gradeRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Grade.class, "id", id)),
                GradeDto.class);
    }

    @Override
    public GradeDto createGrade(GradeCreateDto gradeCreateDto) {
        Grade grade = mapper.getModelMapper().map(gradeCreateDto, Grade.class);
        School school = schoolRepository.findById(gradeCreateDto.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException(School.class, "id", gradeCreateDto.getSchoolId()));
        grade.setSchool(school);
        return mapper.getModelMapper().map(gradeRepository.save(grade), GradeDto.class);
    }

    @Override
    public GradeDto updateGrade(GradeUpdateDto gradeUpdateDto) {
        return gradeRepository.findById(gradeUpdateDto.getId())
                .map(existingGrade -> {
                    mapper.getModelMapper().map(gradeUpdateDto, existingGrade);
                    School school = schoolRepository.findById(gradeUpdateDto.getSchoolId())
                            .orElseThrow(() -> new EntityNotFoundException(School.class, "id", gradeUpdateDto.getSchoolId()));
                    existingGrade.setSchool(school);
                    return mapper.getModelMapper().map(gradeRepository.save(existingGrade), GradeDto.class);
                }).orElseThrow(() -> new EntityNotFoundException(Grade.class, "id", gradeUpdateDto.getId()));
    }

    @Override
    public void deleteGrade(long id) {
        gradeRepository.deleteById(id);
    }
    
}
