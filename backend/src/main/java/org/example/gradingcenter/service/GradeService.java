package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Grade;

import java.util.List;

public interface GradeService {

    List<Grade> getGrades();

    Grade getGrade(long id);

    Grade createGrade(Grade Grade);

    Grade updateGrade(Grade Grade, long id);

    void deleteGrade(long id);
    
}
