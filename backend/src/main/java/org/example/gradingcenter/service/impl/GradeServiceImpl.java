package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.GradeRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    
    private final GradeRepository gradeRepository;
    private final EntityMapper mapper;

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade getGrade(long id) {
        return gradeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Grade.class, "id", id));
    }

    @Override
    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Grade grade, long id) {
        return this.gradeRepository.findById(id)
                .map(grade1 -> {
                    mapper.mapGradeUpdateDtoToGrade(grade, grade1);
                    return this.gradeRepository.save(grade1);
                }).orElseGet(() ->
                        this.gradeRepository.save(grade)
                );
    }

    @Override
    public void deleteGrade(long id) {
        gradeRepository.deleteById(id);
    }
    
}
