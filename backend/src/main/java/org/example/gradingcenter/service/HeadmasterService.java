package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.EmployeeDto;
import org.example.gradingcenter.data.dto.users.EmployeeInDto;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface HeadmasterService {

    List<EmployeeDto> getHeadmasters();

    List<EmployeeDto> filterHeadmasters(Specification<Headmaster> specification);

    EmployeeDto getHeadmaster(long id);

    EmployeeDto createHeadmaster(Long userId);

    void updateHeadmaster(EmployeeInDto headmasterInDto, long id) throws EntityNotFoundException;

    void deleteHeadmaster(long id);

    Headmaster fetchHeadmaster(long id);
}
