package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Headmaster;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface HeadmasterService extends UserDetailsService {

    List<Headmaster> getHeadmasters();

    Headmaster getHeadmaster(long id);

    Headmaster createHeadmaster(Headmaster headmaster);

    Headmaster updateHeadmaster(Headmaster headmaster, long id);

    void deleteHeadmaster(long id);
    
}
