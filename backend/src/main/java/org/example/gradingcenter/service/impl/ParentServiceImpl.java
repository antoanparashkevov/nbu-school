package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.repository.ParentRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ParentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<Parent> getParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParent(long id) {
        return parentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parent.class, "id", id));
    }

    @Override
    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public Parent updateParent(Parent parent, long id) {
        return this.parentRepository.findById(id)
                .map(parent1 -> {
                    mapperConfig.getModelMapper().map(parent, parent1);
                    return this.parentRepository.save(parent1);
                }).orElseGet(() ->
                        this.parentRepository.save(parent)
                );
    }

    @Override
    public void deleteParent(long id) {
        parentRepository.deleteById(id);
    }
    
}
