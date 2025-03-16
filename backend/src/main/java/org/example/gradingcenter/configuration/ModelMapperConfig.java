package org.example.gradingcenter.configuration;

import org.example.gradingcenter.data.dto.HeadmasterDto;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration(enforceUniqueMethods = false)
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        addRoleMappings(modelMapper);
        addHeadmasterMappings(modelMapper);
        addSchoolMappings(modelMapper);
        return modelMapper;
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> getModelMapper().map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static final Converter<Role, String> ROLE_TO_STRING =
            context -> {
                Role source = context.getSource();
                return (source == null) ? null : source.getAuthority();
            };

    private static void addRoleMappings(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Role.class, String.class).setConverter(ROLE_TO_STRING);
    }

    private static void addHeadmasterMappings(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Headmaster, HeadmasterDto>() {
            @Override
            protected void configure() {
                map(source.getSchool().getId(), destination.getSchoolId());
            }
        });
    }

    private static void addSchoolMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(SchoolDto.class, School.class)
                .addMappings(mapper -> {
                    mapper.skip(School::setId);
                });
    }

}

