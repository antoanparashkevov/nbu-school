package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByNameAndSchool_Name(String name, String schoolName);

    Optional<Grade> findByNameAndSchool_Id(String name, Long schoolId);

}
