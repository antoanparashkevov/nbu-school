package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.Subject;
import org.example.gradingcenter.data.repository.SubjectRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubject(long id) {
        return subjectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Subject.class, "id", id));
    }

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject, long id) {
        return this.subjectRepository.findById(id)
                .map(subjectToUpdate -> {
                    mapperConfig.getModelMapper().map(subject, subjectToUpdate);
                    return this.subjectRepository.save(subjectToUpdate);
                }).orElseGet(() ->
                        this.subjectRepository.save(subject)
                );
    }

    @Override
    public void deleteSubject(long id) {
        subjectRepository.deleteById(id);
    }
    
}
