package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.users.Parent;

import java.util.List;

public interface ParentService {

    List<Parent> getParents();

    Parent getParent(long id);

    Parent createParent(Parent Parent);

    Parent updateParent(Parent Parent, long id);

    void deleteParent(long id);
    
}
