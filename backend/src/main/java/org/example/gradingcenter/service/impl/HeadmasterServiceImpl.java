package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Headmaster;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.HeadmasterRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.HeadmasterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeadmasterServiceImpl implements HeadmasterService {

    private final HeadmasterRepository headmasterRepository;
    private final EntityMapper mapper;

    @Override
    public List<Headmaster> getHeadmasters() {
        return headmasterRepository.findAll();
    }

    @Override
    public Headmaster getHeadmaster(long id) {
        return headmasterRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Headmaster.class, "id", id));
    }

    @Override
    public Headmaster createHeadmaster(Headmaster headmaster) {
        return headmasterRepository.save(headmaster);
    }

    @Override
    public Headmaster updateHeadmaster(Headmaster headmaster, long id) {
        return this.headmasterRepository.findById(id)
                .map(headmaster1 -> {
                    mapper.mapHeadmasterUpdateDtoToHeadmaster(headmaster, headmaster1);
                    return this.headmasterRepository.save(headmaster1);
                }).orElseGet(() ->
                        this.headmasterRepository.save(headmaster)
                );
    }

    @Override
    public void deleteHeadmaster(long id) {
        headmasterRepository.deleteById(id);
    }

}
