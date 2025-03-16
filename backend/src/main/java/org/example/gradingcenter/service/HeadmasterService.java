package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.HeadmasterDto;
import org.example.gradingcenter.data.entity.users.Headmaster;

import java.util.List;

public interface HeadmasterService {

    List<HeadmasterDto> getHeadmasters();

    HeadmasterDto getHeadmaster(long id);

    HeadmasterDto createHeadmaster(Long userId);

    void deleteHeadmaster(long id);

    Headmaster fetchHeadmaster(long id);
}
