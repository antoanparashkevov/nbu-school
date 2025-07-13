package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.ProgramSlot;

import java.util.List;

public interface ProgramService {

    List<ProgramSlot> getPrograms();

    ProgramSlot getProgram(long id);

    ProgramSlot createProgram(ProgramSlot ProgramSlot);

    ProgramSlot updateProgram(ProgramSlot ProgramSlot, long id);

    void deleteProgram(long id);
    
}
