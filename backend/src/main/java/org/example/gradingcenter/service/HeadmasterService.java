package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.users.Headmaster;

import java.util.List;

public interface HeadmasterService {

    List<Headmaster> getHeadmasters();

    Headmaster getHeadmaster(long id);

    Headmaster createHeadmaster(Headmaster headmaster);

    Headmaster updateHeadmaster(Headmaster headmaster, long id);

    void deleteHeadmaster(long id);
    
}
