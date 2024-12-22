package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
