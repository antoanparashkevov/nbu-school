package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
