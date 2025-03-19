package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.GradeCreateDto;
import org.example.gradingcenter.data.dto.GradeDto;

import java.util.List;

public interface GradeService {

    List<GradeDto> getGrades();

    GradeDto getGrade(long id);

    GradeDto createGrade(GradeCreateDto gradeCreateDto);

    GradeDto updateGrade(GradeDto gradeUpdateDto);

    void deleteGrade(long id);
    
}
