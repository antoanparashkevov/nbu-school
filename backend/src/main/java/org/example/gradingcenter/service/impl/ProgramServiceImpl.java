package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.entity.ProgramSlot;
import org.example.gradingcenter.data.repository.ProgramRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<ProgramSlot> getPrograms() {
        return programRepository.findAll();
    }

    @Override
    public ProgramSlot getProgram(long id) {
        return programRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProgramSlot.class, "id", id));
    }

    @Override
    public ProgramSlot createProgram(ProgramSlot programSlot) {
        return programRepository.save(programSlot);
    }

    @Override
    public ProgramSlot updateProgram(ProgramSlot programSlot, long id) {
        return this.programRepository.findById(id)
                .map(programSlot1 -> {
                    mapperConfig.getModelMapper().map(programSlot, programSlot1);
                    return this.programRepository.save(programSlot1);
                }).orElseGet(() ->
                        this.programRepository.save(programSlot)
                );
    }

    @Override
    public void deleteProgram(long id) {
        programRepository.deleteById(id);
    }
    
}
