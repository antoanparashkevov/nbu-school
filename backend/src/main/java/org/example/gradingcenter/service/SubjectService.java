package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> getSubjects();

    Subject getSubject(long id);

    Subject createSubject(Subject Subject);

    Subject updateSubject(Subject Subject, long id);

    void deleteSubject(long id);
    
}
