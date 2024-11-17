package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.Headmaster;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeadmasterMapper extends UserMapper {

    private final ModelMapper modelMapper;

    public void mapHeadmasterUpdateDtoToHeadmaster(final Headmaster headmasterFrom, Headmaster headmasterTo) {
        modelMapper.map(headmasterFrom, headmasterTo);
    }

}
