package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.SubjectDto;
import org.example.gradingcenter.data.dto.SubjectOutDto;

import java.util.List;

public interface SubjectService {

    List<SubjectOutDto> getSubjects();

    SubjectOutDto getSubject(long id);

    SubjectOutDto createSubject(SubjectDto Subject);

    SubjectOutDto updateSubject(SubjectDto Subject, long id);

    void deleteSubject(long id);
    
}
