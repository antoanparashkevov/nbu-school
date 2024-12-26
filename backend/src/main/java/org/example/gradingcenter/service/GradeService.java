package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.GradeCreateDto;
import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.GradeUpdateDto;

import java.util.List;

public interface GradeService {

    List<GradeDto> getGrades();

    GradeDto getGrade(long id);

    GradeDto createGrade(GradeCreateDto gradeCreateDto);

    GradeDto updateGrade(GradeUpdateDto gradeUpdateDto);

    void deleteGrade(long id);
    
}
