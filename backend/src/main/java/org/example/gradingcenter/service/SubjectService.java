package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SubjectDto;
import org.example.gradingcenter.data.dto.SubjectOutDto;
import org.example.gradingcenter.exceptions.DuplicateEntityException;

import java.util.List;

public interface SubjectService {

    List<SubjectOutDto> getSubjects();

    List<SubjectOutDto> getSubjects(String gradeName, Long schoolId);

    SubjectOutDto getSubject(long id);

    List<SubjectOutDto> getSubjectsByTeacher(long teacherId);

    SubjectOutDto createSubject(SubjectDto Subject) throws DuplicateEntityException;

    SubjectOutDto updateSubject(SubjectDto Subject, long id);

    void deleteSubject(long id);
    
}
