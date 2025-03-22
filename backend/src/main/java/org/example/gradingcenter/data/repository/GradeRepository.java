package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByNameAndSchoolName(String name, String schoolName);

}
