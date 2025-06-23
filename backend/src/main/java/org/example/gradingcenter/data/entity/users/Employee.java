package org.example.gradingcenter.data.entity.users;

import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.School;

import java.util.Set;

public interface Employee {

    long getId();

    String getFirstName();

    String getLastName();

    String getUsername();

    Set<Role> getAuthorities();

    School getSchool();

}
