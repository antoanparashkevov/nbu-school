package org.example.gradingcenter.service;

import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.entity.enums.Roles;

public interface AuthorizationService {
    UserOutDto getLoggedInUser();

    boolean hasAnyRole(Roles... roles);
}
