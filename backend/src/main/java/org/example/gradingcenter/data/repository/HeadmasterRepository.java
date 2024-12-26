package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadmasterRepository extends JpaRepository<Headmaster, Long> {
}
