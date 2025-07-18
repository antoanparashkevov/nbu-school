package org.example.gradingcenter.configuration;

import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.dto.users.HeadmasterDto;
import org.example.gradingcenter.data.dto.users.ParentDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.dto.users.EmployeeDto;
import org.example.gradingcenter.data.entity.BaseEntity;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration(enforceUniqueMethods = false)
public class ModelMapperConfig {

    public static final Converter<Role, String> ROLE_TO_STRING =
            context -> {
                Role source = context.getSource();
                return (source == null) ? null : source.getAuthority();
            };

    private static final Converter<List<BaseEntity>, List<Long>> ENTITY_IDS_CONVERTER =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList());

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        addRoleMappings(modelMapper);
        addSchoolMappings(modelMapper);
        addGradeMappings(modelMapper);
        addStudentMappings(modelMapper);
        addParentMappings(modelMapper);
        return modelMapper;
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> getModelMapper().map(element, targetClass))
                .collect(Collectors.toList());
    }

    private static void addRoleMappings(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Role.class, String.class).setConverter(ROLE_TO_STRING);
    }

    private static void addGradeMappings(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Grade, GradeDto>() {
            @Override
            protected void configure() {
                map(source.getSchool().getId(), destination.getSchoolId());
            }
        });
    }

    private static void addStudentMappings(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Student, StudentOutDto>() {
            @Override
            protected void configure() {
                map(source.getGrade(), destination.getGrade());
                map(source.getGrade().getSchool(), destination.getSchool());
                using(ENTITY_IDS_CONVERTER).map(source.getParents(), destination.getParentIds());
            }
        });
    }

    private static void addParentMappings(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Parent, ParentDto>() {
            @Override
            protected void configure() {
                using(ENTITY_IDS_CONVERTER).map(source.getChildren(), destination.getChildrenIds());
            }
        });
    }

    private static void addSchoolMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(SchoolDto.class, School.class).addMappings(mapper -> {mapper.skip(School::setId);});
    }

}

