package org.example.gradingcenter.service.impl;

import lombok.AllArgsConstructor;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.exceptions.AuthenticationFailureException;
import org.example.gradingcenter.service.AuthorizationService;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public UserOutDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            User loggedInUser = (User) authentication.getPrincipal();
            return MapperUtil.entityToDto(loggedInUser);
        }
        throw new AuthenticationFailureException("There is not a logged in user");
    }

    @Override
    public boolean hasAnyRole(Roles... roles) {
        UserOutDto loggedUser = getLoggedInUser();
        for (Roles role : roles) {
            if (loggedUser.getAuthorities().contains(role.toString())) {
                return true;
            }
        }
        return false;
    }

}
