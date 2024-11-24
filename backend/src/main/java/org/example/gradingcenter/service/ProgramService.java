package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Program;

import java.util.List;

public interface ProgramService {

    List<Program> getPrograms();

    Program getProgram(long id);

    Program createProgram(Program Program);

    Program updateProgram(Program Program, long id);

    void deleteProgram(long id);
    
}
