package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.School;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolMapper {

    private final ModelMapper modelMapper;

    public void mapSchoolUpdateDtoToSchool(School schoolFrom, School schoolTo) {
        modelMapper.map(schoolFrom, schoolTo);
    }

}
