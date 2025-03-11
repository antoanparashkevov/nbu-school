package org.example.gradingcenter.configuration;

import org.example.gradingcenter.data.entity.Role;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration(enforceUniqueMethods = false)
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addRoleMappings(modelMapper);
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

}

