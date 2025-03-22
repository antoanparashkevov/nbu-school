package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.entity.users.Parent;

import java.util.List;

public interface ParentService {

    List<ParentDto> getParents();

    ParentDto getParent(long id);

    Parent fetchParent(long id);

    ParentDto createParent(Long userId);

    void deleteParent(long id);
    
}
