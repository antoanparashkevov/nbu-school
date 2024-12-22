package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Program;
import org.example.gradingcenter.data.mappers.EntityMapper;
import org.example.gradingcenter.data.repository.ProgramRepository;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final EntityMapper mapper;

    @Override
    public List<Program> getPrograms() {
        return programRepository.findAll();
    }

    @Override
    public Program getProgram(long id) {
        return programRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Program.class, "id", id));
    }

    @Override
    public Program createProgram(Program program) {
        return programRepository.save(program);
    }

    @Override
    public Program updateProgram(Program program, long id) {
        return this.programRepository.findById(id)
                .map(program1 -> {
                    mapper.mapProgramUpdateDtoToProgram(program, program1);
                    return this.programRepository.save(program1);
                }).orElseGet(() ->
                        this.programRepository.save(program)
                );
    }

    @Override
    public void deleteProgram(long id) {
        programRepository.deleteById(id);
    }
    
}
