package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.MarkDto;
import org.example.gradingcenter.data.dto.MarkOutDto;

import java.util.List;

public interface MarkService {
    
    List<MarkOutDto> getMarks();

    MarkOutDto getMark(long id);

    MarkOutDto createMark(MarkDto mark);

    void deleteMark(long id);
    
}
