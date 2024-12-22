package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
