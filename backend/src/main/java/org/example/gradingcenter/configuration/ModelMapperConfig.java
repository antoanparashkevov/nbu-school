package org.example.gradingcenter.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static void addSpecialityMappings(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Speciality.class, SpecialityDto.class).setConverter(MapConverters.SPECIALITY_TO_STRING);
    }
}

