package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Mark;

import java.util.List;

public interface MarkService {
    
    List<Mark> getMarks();

    Mark getMark(long id);

    Mark createMark(Mark Mark);

    Mark updateMark(Mark Mark, long id);

    void deleteMark(long id);
    
}
