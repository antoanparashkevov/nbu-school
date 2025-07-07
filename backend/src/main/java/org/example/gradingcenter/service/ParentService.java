package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ParentService {

    List<ParentDto> getParents();

    List<ParentDto> getParents(List<Long> parentIds);

    List<ParentDto> getParents(Long childId);

    List<ParentDto> filterParents(Specification<Parent> specification);

    ParentDto getParent(long id);

    Parent fetchParent(long id);

    ParentDto createParent(Long userId);

    ParentDto updateParent(ParentDto parentDto, long id) throws EntityNotFoundException;

    void addChildToParent(Long parentId, String childEgn);

    void removeChildFromParent(Long parentId, Long childId);

    void deleteParent(long id);
    
}
