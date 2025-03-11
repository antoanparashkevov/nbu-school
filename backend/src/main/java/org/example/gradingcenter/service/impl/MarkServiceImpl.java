package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.Mark;
import org.example.gradingcenter.data.repository.MarkRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.MarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<Mark> getMarks() {
        return markRepository.findAll();
    }

    @Override
    public Mark getMark(long id) {
        return markRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Mark.class, "id", id));
    }

    @Override
    public Mark createMark(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public Mark updateMark(Mark mark, long id) {
        return this.markRepository.findById(id)
                .map(mark1 -> {
                    mapperConfig.getModelMapper().map(mark, mark1);
                    return this.markRepository.save(mark1);
                }).orElseGet(() ->
                        this.markRepository.save(mark)
                );
    }

    @Override
    public void deleteMark(long id) {
        markRepository.deleteById(id);
    }
    
}
