package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.ProgramSlotDto;
import org.example.gradingcenter.data.entity.ProgramSlot;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface ProgramService {

    List<ProgramSlot> getPrograms();

    ProgramSlot filterPrograms(long id);

    Map<DayOfWeek, List<ProgramSlotDto>> filterPrograms(Specification<ProgramSlot> specification);

    ProgramSlot createProgram(ProgramSlot ProgramSlot);

    ProgramSlot updateProgram(ProgramSlot ProgramSlot, long id);

    void deleteProgram(long id);
    
}
