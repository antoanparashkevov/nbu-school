package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
