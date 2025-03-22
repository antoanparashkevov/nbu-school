package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;

public interface RoleService {
    Role fetchRole(Roles authority);
}
